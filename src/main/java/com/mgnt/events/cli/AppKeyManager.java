package com.mgnt.events.cli;

import java.io.IOException;
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

  private Properties loadProperties() throws IOException {
    Properties _properties = new Properties();
    if (!Files.exists(_envFile)) {
      Path _parent = _envFile.getParent();
    }

    return _properties;
  }
}
