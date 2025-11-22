package com.mgnt.events.responses.files;

import java.time.LocalDateTime;
import java.util.UUID;

public record FileUploadResponse(
  UUID id,
  String fileName,
  String storageKey,
  String bucket,
  Long size,
  String contentType,
  String notes,
  String url,
  LocalDateTime createdAt,
  LocalDateTime updatedAt,
  LocalDateTime deletedAt
) {}
