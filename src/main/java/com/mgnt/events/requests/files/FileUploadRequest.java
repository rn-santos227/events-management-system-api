package com.mgnt.events.requests.files;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public record FileUploadRequest(
  @NotNull(message = "File is required") MultipartFile file,
  String notes
) {}
