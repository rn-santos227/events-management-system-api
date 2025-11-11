package com.mgnt.events.constants;

public final class JsonPaths {
  private JsonPaths() {}

  public static final String ACCESS_TOKEN = "$.accessToken";
  public static final String TOKEN_TYPE = "$.tokenType";
  public static final String EXPIRES_AT = "$.expiresAt";
  public static final String MESSAGE = "$.message";
  public static final String ID = "$.id";
  public static final String NAME = "$.name";

  public static final String INDEX_0_ID = "$[0].id";
  public static final String INDEX_0_EMAIL = "$[0].email";
  public static final String INDEX_0_NAME = "$[0].name";
  public static final String INDEX_0_PRIVILEGES = "$[0].privileges[0].name";
  public static final String INDEX_0_ROLE_ID = "$[0].role.id";
  public static final String INDEX_0_ROLE_NAME = "$[0].role.name";
}
