package com.mgnt.events.cli;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

import com.mgnt.events.constants.Seeds;

public final class AppKeyCommand {
  private static final String _DEFAULT_ENV_FILE = ".env.properties";
  
  private AppKeyCommand() {}
  public static void main(String[] args) {
    Map<String, String> _options = Collections.emptyMap();
    try {
      _options = CliOptionParser.parse(args);
    } catch (IllegalArgumentException exception) {
      System.err.println(exception.getMessage());
      printUsage();
      System.exit(1);
    }

    Path _envFile = Paths.get(_options.getOrDefault(Seeds.ENV_KEY, _DEFAULT_ENV_FILE));
    boolean _force = Boolean.parseBoolean(_options.getOrDefault(Seeds.FORCE_KEY, Seeds.FORCE_DEFAULT));

    AppKeyManager _manager = new AppKeyManager(_envFile);
    try {
      if (_manager.hasExistingKey() && !_force) {
        System.out.printf(
          "APP_KEY already exists in %s. Use --force to overwrite.%n",
          _envFile.toAbsolutePath()
        );
        return;
      }
    } catch (IOException exception) {
      System.err.printf("Failed to update %s: %s%n", _envFile, exception.getMessage());
      exception.printStackTrace(System.err);
      System.exit(1);
    }
  }

  private static void printUsage() {
    System.out.println("Usage: ./gradlew generateAppKey [-PappKeyEnvFile=path] [-PappKeyForce=true]");
  }
}
