package com.mgnt.events.services;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.mgnt.events.config.storage.StorageProperties;
import com.mgnt.events.constants.Formats;
import com.mgnt.events.models.StoredFile;
import com.mgnt.events.repositories.StoredFileRepository;
import com.mgnt.events.responses.files.FileUploadResponse;

import software.amazon.awssdk.services.s3.S3Client;

@Service
public class FileStorageService {
  private static final DateTimeFormatter _DIRECTORY_FORMATTER = DateTimeFormatter.ofPattern(Formats.DATE_FORMAT);

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
