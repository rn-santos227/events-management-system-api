package com.mgnt.events.cli;

import java.security.SecureRandom;
import java.util.Base64;

final class AppKeyManager {
  static final String _PROPERTY = "APP_KEY";
  private static final Base64.Encoder _ENCODER = Base64.getEncoder().withoutPadding();
  private static final SecureRandom _RANDOM = new SecureRandom();
}
