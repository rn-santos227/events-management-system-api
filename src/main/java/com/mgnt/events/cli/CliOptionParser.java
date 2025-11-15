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

      String key;
      String value;
      int equalsIndex = body.indexOf('=');
      if (equalsIndex >= 0) {
        key = body.substring(0, equalsIndex);
        value = body.substring(equalsIndex + 1);
      } else {
        key = body;
        value = "true";
      }

      key = normalizeKey(key);
      values.put(key, value);
    }

    return values;
  }

  private static String normalizeKey(String key) {
    return Objects.requireNonNull(key, "Argument name must not be null").trim().toLowerCase();
  }
}
