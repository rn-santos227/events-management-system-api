package com.mgnt.events.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.config.storage.StorageProperties;
import com.mgnt.events.constants.Patterns;
import com.mgnt.events.constants.Storage;
import com.mgnt.events.models.StoredFile;
import com.mgnt.events.repositories.StoredFileRepository;
import com.mgnt.events.responses.files.FileUploadResponse;
import com.mgnt.events.utils.RequestValidators;

import jakarta.transaction.Transactional;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
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

  @Transactional
  public FileUploadResponse upload(MultipartFile multipartFile, @Nullable String note) {
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

    MultipartFile ensuredFile = RequestValidators.requireNonNull(multipartFile, "File");
    if (ensuredFile.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File must not be empty");
    }

    String _bucket = _storageProperties.getBucket();
    if (RequestValidators.isBlank(_bucket)) {
      throw new ResponseStatusException(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Storage bucket is not configured"
      );
    }

    String _sanitizedBucket = _bucket.trim();
    String _normalizedFileName = normalizeFileName(ensuredFile.getOriginalFilename(), ensuredFile.getName());
    String _objectKey = generateObjectKey(_normalizedFileName);

    PutObjectRequest.Builder _requestBuilder = PutObjectRequest
      .builder()
      .bucket(_sanitizedBucket)
      .key(_objectKey)
      .contentLength(ensuredFile.getSize());

    if (!RequestValidators.isBlank(ensuredFile.getContentType())) {
      _requestBuilder.contentType(ensuredFile.getContentType());
    }

    try {
      _s3Client.putObject(
      _requestBuilder.build(),
      RequestBody.fromInputStream(ensuredFile.getInputStream(), ensuredFile.getSize())
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
  }

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
