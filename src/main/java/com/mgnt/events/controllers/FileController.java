package com.mgnt.events.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
import com.mgnt.events.responses.files.FileUploadResponse;
import com.mgnt.events.services.FileStorageService;
import com.mgnt.events.util.RequestValidators;

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
}
