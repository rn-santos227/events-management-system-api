package com.mgnt.events.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.mgnt.events.config.storage.StorageProperties;
import com.mgnt.events.constants.Patterns;
import com.mgnt.events.constants.Storage;
import com.mgnt.events.models.StoredFile;
import com.mgnt.events.repositories.StoredFileRepository;
import com.mgnt.events.responses.files.FileUploadResponse;
import com.mgnt.events.utils.RequestValidators;

import software.amazon.awssdk.services.s3.S3Client;

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
      return "%s/%s/%s".formatted(sanitizedEndpoint, bucket, key);
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
