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

  public static final class Roles {
    public static final String NAME_ADMIN = "Admin";
    public static final String PRIVILEGE_MANAGE_USERS = "Manage Users";
    public static final String PRIVILEGE_SCOPE_USERS = "users";
    public static final String PRIVILEGE_ACTION_WRITE = "WRITE";
    public static final String ROLE_NAME_MANAGER = "Manager";
    public static final String ROLE_NAME_STAFF = "Staff";
    public static final String PAGINATION = "5";

    private Roles() {}
  }
}
