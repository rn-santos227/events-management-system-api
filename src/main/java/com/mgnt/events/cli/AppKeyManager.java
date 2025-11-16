package com.mgnt.events.cli;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.Properties;

final class AppKeyManager {
  static final String _PROPERTY = "APP_KEY";
  private static final Base64.Encoder _ENCODER = Base64.getEncoder().withoutPadding();
  private static final SecureRandom _RANDOM = new SecureRandom();

  private final Path _envFile;

  AppKeyManager(Path envFile) {
    this._envFile = Objects.requireNonNull(envFile, "Environment file path must not be null");
  }

  String generateKey() {
    byte[] buffer = new byte[32];
    _RANDOM.nextBytes(buffer);
    return "base64:" + _ENCODER.encodeToString(buffer);
  }

  boolean hasExistingKey() throws IOException {
    if (!Files.exists(_envFile)) {
      return false;
    }

    Properties properties = loadProperties();
    String value = properties.getProperty(_PROPERTY);
    return value != null && !value.isBlank();
  }

  private Properties loadProperties() throws IOException {
    Properties _properties = new Properties();
    if (!Files.exists(_envFile)) {
      Path _parent = _envFile.getParent();
      if (_parent != null) {
        Files.createDirectories(_parent);
      }
      Files.createFile(_envFile);
      return _properties;
    }

    try (InputStream inputStream = Files.newInputStream(_envFile)) {
      _properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
    return _properties;
  }
}
