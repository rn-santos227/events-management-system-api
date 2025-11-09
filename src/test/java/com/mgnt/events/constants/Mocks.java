package com.mgnt.events.constants;

public final class Mocks {
  private Mocks() {}

  public static final class Auth {
    public static final String EMAIL = "user@example.com";
    public static final String PASSWORD = "password123";
    public static final String TOKEN_VALUE = "token-value";
    public static final String TOKEN_TYPE = "Bearer";

    private Auth() {}
  }

  public static final class Messages {
    public static final String TEST = "this is a test";

    private Messages() {}
  }
}
