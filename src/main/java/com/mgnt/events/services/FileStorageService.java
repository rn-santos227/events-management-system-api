package com.mgnt.events.services;

import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Formats;

@Service
public class FileStorageService {
  private static final DateTimeFormatter _DIRECTORY_FORMATTER = DateTimeFormatter.ofPattern(Formats.DATE_FORMAT);
}
