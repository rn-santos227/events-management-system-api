package com.mgnt.events.cli;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public final class AppKeyCommand {
  private static final String _DEFAULT_ENV_FILE = ".env.properties";
  
  private AppKeyCommand() {}

  public static void main(String[] args) {
    Map<String, String> _options;
    try {
      _options = CliOptionParser.parse(args);
    } catch (IllegalArgumentException exception) {
      System.err.println(exception.getMessage());
      printUsage();
      System.exit(1);
    }

    Path _envFile = Paths.get(_options.getOrDefault("env-file", _DEFAULT_ENV_FILE));
    boolean _force = Boolean.parseBoolean(_options.getOrDefault("force", "false"));
  }

  private static void printUsage() {
    System.out.println("Usage: ./gradlew generateAppKey [-PappKeyEnvFile=path] [-PappKeyForce=true]");
  }
}
