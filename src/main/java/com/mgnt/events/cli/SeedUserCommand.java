package com.mgnt.events.cli;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.mgnt.events.EventsApiApplication;

public final class SeedUserCommand {
  private SeedUserCommand() {}
  
  public static void main(String[] args) {
    SeedUserOptions _options;
    try {
      _options = SeedUserOptions.from(args);
    } catch (IllegalArgumentException exception) {
      System.err.println(exception.getMessage());
      printUsage();
      System.exit(1);
      return;
    }

    ConfigurableApplicationContext _context = new SpringApplicationBuilder(EventsApiApplication.class)
      .web(WebApplicationType.NONE)
      .logStartupInfo(false)
      .run();
  }

  private static void printUsage() {
    System.out.println("Usage: ./gradlew seedUser -PseedEmail=<email> -PseedPassword=<password> [...]");
  }
}
