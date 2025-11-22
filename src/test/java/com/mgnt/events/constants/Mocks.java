package com.mgnt.events.constants;

public final class Mocks {
  private Mocks() {}

  public static final class Auth {
    public static final String EMAIL = "user@example.com";
    public static final String PASSWORD = "password123";
    public static final String TOKEN_VALUE = "token-value";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String AUTHORIZATION_HEADER = TOKEN_TYPE + " " + TOKEN_VALUE;

    private Auth() {}
  }

  public static final class Messages {
    public static final String TEST = "this is a test";
    public static final String LOGOUT_SUCCESS = "Successfully logged out";

    private Messages() {}
  }

  public static final class Roles {
    public static final String ID_ADMIN = "11111111-1111-1111-1111-111111111111";
    public static final String NAME_ADMIN = "Admin";
    public static final String ID_STAFF = "22222222-2222-2222-2222-222222222222";
    public static final String PRIVILEGE_MANAGE_USERS = "Manage Users";
    public static final String PRIVILEGE_MANAGE_USERS_ID = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
    public static final String PRIVILEGE_READ_USERS_ID = "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb";
    public static final String PRIVILEGE_SCOPE_USERS = "users";
    public static final String PRIVILEGE_ACTION_WRITE = "WRITE";
    public static final String ROLE_NAME_MANAGER = "Manager";
    public static final String ROLE_NAME_STAFF = "Staff";
    public static final String PAGINATION = "5";

    private Roles() {}
  }

  public static final class Users {
    public static final String ID_JANE = "33333333-3333-3333-3333-333333333333";
    public static final String ID_JOHN = "44444444-4444-4444-4444-444444444444";
    public static final String EMAIL_JANE = "jane.doe@example.com";
    public static final String EMAIL_JOHN = "john.smith@example.com";
    public static final String PASSWORD_JOHN = "password123";
    public static final String FIRST_NAME_JANE = "Jane";
    public static final String LAST_NAME_JANE = "Doe";
    public static final String FIRST_NAME_JOHN = "John";
    public static final String LAST_NAME_JOHN = "Smith";
    public static final String PHONE_PRIMARY = "1234567890";
    public static final String PHONE_SECONDARY = "0987654321";
    public static final String PAGINATION = "10";

    private Users() {}
  }
}
