package com.mgnt.events.cli;

import java.util.LinkedHashMap;
import java.util.Map;

final class CliOptionParser {
  private CliOptionParser() {}

  static Map<String, String> parse(String[] args) {
    Map<String, String> values = new LinkedHashMap<>();
    if (args == null) {
      return values;
    }

    for (String rawArg : args) {

    }

    return values;
  }
}
