package com.mgnt.events.cli;

import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

final class AppKeyManager {
  static final String _PROPERTY = "APP_KEY";
  private static final Base64.Encoder _ENCODER = Base64.getEncoder().withoutPadding();
  private static final SecureRandom _RANDOM = new SecureRandom();

  private final Path _envFile;

  AppKeyManager(Path envFile) {
    this._envFile = Objects.requireNonNull(envFile, "Environment file path must not be null");
  }
}
