package com.mgnt.events.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Patterns;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
import com.mgnt.events.requests.files.FileUploadRequest;
import com.mgnt.events.responses.files.FileUploadResponse;
import com.mgnt.events.services.FileStorageService;
import com.mgnt.events.util.RequestValidators;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Routes.FILES)
public class FileController {
  private final FileStorageService _fileStorageService;
  public FileController(FileStorageService fileStorageService) {
    this._fileStorageService = fileStorageService;
  }

  @GetMapping 
  List<FileUploadResponse> findAll(@RequestParam(name = Queries.LIMIT, required = false) Integer limit) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    return _fileStorageService.findAll(sanitizedLimit);
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public FileUploadResponse upload(@Valid @NonNull @ModelAttribute FileUploadRequest request) {
    return _fileStorageService.upload(request);
  }

  @GetMapping(Routes.DOWNLOAD)
  public ResponseEntity<Resource> download(@PathVariable @NonNull UUID id) {
    FileStorageService.FileDownload _download = _fileStorageService.download(id);
    return ResponseEntity
      .ok()
      .contentType(_download.mediaType())
      .contentLength(_download.contentLength())
      .header(
        HttpHeaders.CONTENT_DISPOSITION,
        Patterns.ATTACHMENTS.formatted(_download.fileName())
      )
      .body(_download.resource());
  }

  @DeleteMapping(Routes.APPEND_ID)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NonNull UUID id) {
    _fileStorageService.delete(id);
  }
}
