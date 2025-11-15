package com.mgnt.events.cli;

import java.util.Map;
import java.util.Objects;

record SeedUserOptions(
  String email,
  String password,
  String firstName,
  String lastName,
  String contactNumber,
  String role,
  boolean active,
  boolean force
) {
  static SeedUserOptions from(String[] args) {

  }

  private static String require(Map<String, String> options, String key) {
    String value = options.get(key);
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Missing required option --" + key);
    }
    return value;
  }

  private static String defaultValue(String value, String fallback) {
    return (value == null || value.isBlank()) ? Objects.requireNonNull(fallback) : value;
  }

  private static String normalize(String value) {
    return value == null ? null : value.trim().toLowerCase();
  }
}
