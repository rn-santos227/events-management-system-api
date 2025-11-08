package com.mgnt.events.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.services.FileStorageService;

@RestController
@RequestMapping(Routes.FILES)
public class FileController {
  private final FileStorageService _fileStorageService;
  public FileController(FileStorageService fileStorageService) {
    this._fileStorageService = fileStorageService;
  }
}
