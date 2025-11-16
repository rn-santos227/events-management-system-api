package com.mgnt.events.cli;

import java.util.Map;

public final class AppKeyCommand {
  private static final String _DEFAULT_ENV_FILE = ".env.properties";
  
  private AppKeyCommand() {}

  public static void main(String[] args) {
    Map<String, String> _options;
    try {
      _options = CliOptionParser.parse(args);
    } catch (IllegalArgumentException exception) {
      
    }
  }

  private static void printUsage() {
    System.out.println("Usage: ./gradlew generateAppKey [-PappKeyEnvFile=path] [-PappKeyForce=true]");
  }
}
