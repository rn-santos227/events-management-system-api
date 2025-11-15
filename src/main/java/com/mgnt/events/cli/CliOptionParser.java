package com.mgnt.events.cli;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

final class CliOptionParser {
  private CliOptionParser() {}

  static Map<String, String> parse(String[] args) {
    Map<String, String> values = new LinkedHashMap<>();
    if (args == null) {
      return values;
    }

    for (String rawArg : args) {
      if (rawArg == null || rawArg.isBlank()) {
        continue;
      }

      String arg = rawArg.trim();
      if (!arg.startsWith("--")) {
        throw new IllegalArgumentException("Invalid argument: " + rawArg);
      }

      String body = arg.substring(2);
      if (body.isBlank()) {
        throw new IllegalArgumentException("Invalid argument: " + rawArg);
      }

    }

    return values;
  }

  private static String normalizeKey(String key) {
    return Objects.requireNonNull(key, "Argument name must not be null").trim().toLowerCase();
  }
}
