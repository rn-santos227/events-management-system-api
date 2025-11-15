package com.mgnt.events.cli;

import java.util.Map;
import java.util.Objects;

import com.mgnt.events.constants.Seeds;

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
    Map<String, String> options = CliOptionParser.parse(args);
    String email = normalize(require(options, Seeds.EMAIL_KEY));
    String password = require(options, Seeds.PASSWORD_KEY).trim();
    String firstName = defaultValue(options.get(Seeds.FIRST_NAME_KEY), Seeds.FIRST_NAME_DEFAULT).trim();
    String lastName = defaultValue(options.get(Seeds.LAST_NAME_KEY), Seeds.LAST_NAME_DEFAULT).trim();
    String contactNumber = defaultValue(options.get(Seeds.CONTACT_KEY), Seeds.CONTACT_DEFAULT).trim();
    String role = defaultValue(options.get(Seeds.ROLE_KEY), Seeds.ROLE_DEFAULT).trim();
    boolean active = Boolean.parseBoolean(defaultValue(options.get(Seeds.ACTIVE_KEY), Seeds.ACTIVE_DEFAULT));
    boolean force = Boolean.parseBoolean(defaultValue(options.get(Seeds.FORCE_KEY), Seeds.FORCE_DEFAULT));

    return new SeedUserOptions(email, password, firstName, lastName, contactNumber, role, active, force);
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
