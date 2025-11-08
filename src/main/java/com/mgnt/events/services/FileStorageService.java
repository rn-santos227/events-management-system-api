package com.mgnt.events.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.config.storage.StorageProperties;
import com.mgnt.events.constants.Patterns;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Storage;
import com.mgnt.events.models.StoredFile;
import com.mgnt.events.repositories.StoredFileRepository;
import com.mgnt.events.requests.files.FileUploadRequest;
import com.mgnt.events.responses.files.FileUploadResponse;
import com.mgnt.events.util.RequestValidators;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class FileStorageService {
  private static final DateTimeFormatter _DIRECTORY_FORMATTER = DateTimeFormatter.ofPattern(Patterns.DATE_FORMAT);

  private final ObjectProvider<S3Client> _s3ClientProvider;
  private final StorageProperties _storageProperties;
  private final StoredFileRepository _storedFileRepository;

  public FileStorageService(
    ObjectProvider<S3Client> s3ObjectProvider,
    StorageProperties storageProperties,
    StoredFileRepository storedFileRepository
  ) {
    this._s3ClientProvider = s3ObjectProvider;
    this._storageProperties = storageProperties;
    this._storedFileRepository = storedFileRepository;
  }

  @Transactional(readOnly = true)
  public List<FileUploadResponse> findAll(@Nullable Integer limit) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    if (sanitizedLimit == null) {
       return _storedFileRepository.findAll().stream().map(this::toResponse).toList();
    }

    return _storedFileRepository
      .findAll(PageRequest.of(0, sanitizedLimit))
      .stream().map(this::toResponse)
      .toList();
  }

  @Transactional(rollbackFor = Throwable.class)
  public void delete(@NonNull Long id) {
    StoredFile storedFile = RequestValidators.requireNonNull(getStoredFile(id), "File") ;
    try {
      _storedFileRepository.delete(storedFile);
    } catch (DataIntegrityViolationException exception) {
      throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        "Unable to delete stored file",
        exception
      );
    }
  }

  @Transactional(rollbackFor = Throwable.class)
  public FileUploadResponse upload(@NonNull FileUploadRequest request) {
    if (!_storageProperties.isEnabled()) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "File storage is disabled");
    }

    S3Client _s3Client = _s3ClientProvider.getIfAvailable(); 
    if (_s3Client == null) {
      throw new ResponseStatusException(
        HttpStatus.SERVICE_UNAVAILABLE,
        "Storage client is not configured"
      );
    }

    FileUploadRequest ensuredRequest = Objects.requireNonNull(request, "File upload request must not be null");

    String _bucket = _storageProperties.getBucket();
    if (RequestValidators.isBlank(_bucket)) {
      throw new ResponseStatusException(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Storage bucket is not configured"
      );
    }

    MultipartFile _ensuredFile = RequestValidators.requireNonNull(ensuredRequest.file(), "File");
    if (_ensuredFile.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File must not be empty");
    }

    String _sanitizedBucket = _bucket.trim();
    String _normalizedFileName = normalizeFileName(_ensuredFile.getOriginalFilename(), _ensuredFile.getName());
    String _objectKey = generateObjectKey(_normalizedFileName);

    PutObjectRequest.Builder _requestBuilder = PutObjectRequest
      .builder()
      .bucket(_sanitizedBucket)
      .key(_objectKey)
      .contentLength(_ensuredFile.getSize());

    if (!RequestValidators.isBlank(_ensuredFile.getContentType())) {
      _requestBuilder.contentType(_ensuredFile.getContentType());
    }

    try {
      _s3Client.putObject(
      _requestBuilder.build(),
      RequestBody.fromInputStream(_ensuredFile.getInputStream(), _ensuredFile.getSize())
      );
    } catch (IOException exception) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read file contents", exception);
    } catch (AwsServiceException | SdkClientException exception) {
      throw new ResponseStatusException(
        HttpStatus.BAD_GATEWAY,
        "Failed to upload file to storage provider",
        exception
      );
    }

    StoredFile _storedFile = new StoredFile();
    String notes = ensuredRequest.notes();
  
    _storedFile.setFileName(_normalizedFileName);
    _storedFile.setStorageKey(_objectKey);
    _storedFile.setBucket(_sanitizedBucket);
    _storedFile.setContentType(_ensuredFile.getContentType());
    _storedFile.setSize(_ensuredFile.getSize());
    _storedFile.setNotes(RequestValidators.isBlank(notes) ? null : notes.trim());
    _storedFile.setUrl(buildFileUrl(_sanitizedBucket, _objectKey));

    return toResponse(_storedFileRepository.save(_storedFile));
  }
  
  @Transactional(readOnly = true)
  public FileDownload download(@NonNull Long id) {
    if (!_storageProperties.isEnabled()) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "File storage is disabled");
    }

    S3Client _s3Client = _s3ClientProvider.getIfAvailable();
    if (_s3Client == null) {
      throw new ResponseStatusException(
        HttpStatus.SERVICE_UNAVAILABLE,
        "Storage client is not configured"
      );
    }

    StoredFile _storedFile = getStoredFile(id);
    GetObjectRequest request = GetObjectRequest
      .builder()
      .bucket(_storedFile.getBucket())
      .key(_storedFile.getUrl())
      .build();

    ResponseInputStream<GetObjectResponse> _s3Object;
    try {
      _s3Object = RequestValidators.requireNonNull(_s3Client.getObject(request), "File Content") ;
    } catch (NoSuchKeyException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stored file contents not found", exception);
    } catch (AwsServiceException | SdkClientException exception) {
      throw new ResponseStatusException(
        HttpStatus.BAD_GATEWAY,
        "Failed to download file from storage provider",
        exception
      );
    }

    String _contentType = _storedFile.getContentType();
    if (RequestValidators.isBlank(_contentType)) {
      _contentType = _s3Object.response().contentType();
    }
    
    MediaType _mediaType = MediaType.APPLICATION_OCTET_STREAM;
    if (!RequestValidators.isBlank(_contentType)) {
      try {
        _mediaType = Optional.ofNullable(_contentType)
          .filter(c -> !RequestValidators.isBlank(c))
          .map(String::trim)
          .map(MediaType::parseMediaType)
          .orElse(MediaType.APPLICATION_OCTET_STREAM);
      } catch (InvalidMediaTypeException ignored) {
        _mediaType = MediaType.APPLICATION_OCTET_STREAM;
      }
    }

    Resource resource = new InputStreamResource(Objects.requireNonNull(_s3Object));
    return new FileDownload(
      resource, 
      RequestValidators.requireNonNull(_mediaType, "Media Type"),
      RequestValidators.requireNonNull(_storedFile.getFileName(), "File Name"),
      _storedFile.getSize()
    );
  }

  public record FileDownload(
    @NonNull Resource resource, 
    @NonNull MediaType mediaType, 
    @NonNull String fileName,
    long contentLength
  ) {}

  private String normalizeFileName(@Nullable String originalFilename, String fallbackName) {
    String candidate = !RequestValidators.isBlank(originalFilename) ? originalFilename : fallbackName;
    if (RequestValidators.isBlank(candidate)) {
      candidate = Storage.CANDIDATE;
    }

    String trimmed = Objects.requireNonNull(candidate).trim().replace("\\", "/");
    int lastSlashIndex = trimmed.lastIndexOf('/');

    if (lastSlashIndex >= 0 && lastSlashIndex < trimmed.length() - 1) {
      return trimmed.substring(lastSlashIndex + 1);
    }
    return lastSlashIndex == trimmed.length() - 1 ? Storage.CANDIDATE : trimmed;
  }

  private String generateObjectKey(String fileName) {
    String sanitized = fileName.replace("\\", "/");
    String extension = "";
    int lastDot = sanitized.lastIndexOf('.');
    if (lastDot >= 0 && lastDot < sanitized.length() - 1) {
      extension = sanitized.substring(lastDot);
    }

    String randomName = UUID.randomUUID().toString();
    String datePath = _DIRECTORY_FORMATTER.format(LocalDate.now());
    return "%s/%s%s".formatted(datePath, randomName, extension);
  }

  private String buildFileUrl(String bucket, String key) {
    String endpoint = _storageProperties.getEndpoint();
    if (!RequestValidators.isBlank(endpoint)) {
      String sanitizedEndpoint = endpoint.trim();
      if (sanitizedEndpoint.endsWith("/")) {
        sanitizedEndpoint = sanitizedEndpoint.substring(0, sanitizedEndpoint.length() - 1);
      }
      return Patterns.STORAGE_PATTERN.formatted(sanitizedEndpoint, bucket, key);
    }

    String region = _storageProperties.getRegion();
    if (RequestValidators.isBlank(region)) {
      region = Storage.DEFAULT_REGION;
    }
    return Patterns.AWS_PATTERN.formatted(bucket, region.trim(), key);
  }

  private StoredFile getStoredFile(@NonNull Long id) {
    return Objects.requireNonNull(
      _storedFileRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"))
    );
  }

  private FileUploadResponse toResponse(StoredFile storedFile) {
    StoredFile ensuredFile = Objects.requireNonNull(storedFile, "Stored file must not be null");
    return new FileUploadResponse(
      Objects.requireNonNull(ensuredFile.getId(), "File identifier must not be null"),
      ensuredFile.getFileName(),
      ensuredFile.getStorageKey(),
      ensuredFile.getBucket(),
      ensuredFile.getSize(),
      ensuredFile.getContentType(),
      ensuredFile.getNotes(),
      ensuredFile.getUrl(),
      ensuredFile.getCreatedAt(),
      ensuredFile.getUpdatedAt(),
      ensuredFile.getDeletedAt()
    );
  }
}
