package com.mgnt.events.cli;

import org.springframework.beans.BeansException;
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

    int exitCode = 0;
    try {
      UserSeeder seeder = _context.getBean(UserSeeder.class);
      seeder.seed(_options);
    } catch (BeansException exception) {
      exitCode = 1;
      System.err.println("Failed to seed user: " + exception.getMessage());
      exception.printStackTrace(System.err);
    } finally {
      _context.close();
    }

    if (exitCode != 0) {
      System.exit(exitCode);
    }
  }

  private static void printUsage() {
    System.out.println("Usage: ./gradlew seedUser -PseedEmail=<email> -PseedPassword=<password> [...]");
  }
}
