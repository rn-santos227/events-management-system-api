package com.mgnt.events.cli;

public final class SeedUserCommand {
  private SeedUserCommand() {}
  
  public static void main(String[] args) {
    SeedUserOptions _options;
    try {
      _options = SeedUserOptions.from(args);
    } catch (IllegalArgumentException exception) {
      System.err.println(exception.getMessage());
    }
  }
}
