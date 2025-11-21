package com.mgnt.events.constants;

public class Routes {
  private Routes() {}

  public static final String API_BASE = "/api";
  public static final String API_VERSION = "/v1";

  public static final String BASE_PATH = API_BASE + API_VERSION;
  public static final String TEST = BASE_PATH + "/test";
  public static final String AUTH = BASE_PATH + "/auth";

  public static final String LOGIN = "/login";
  public static final String AUTH_LOGIN = AUTH + LOGIN;
  public static final String LOGOUT = "/logout";
  public static final String AUTH_LOGOUT = AUTH + LOGOUT;

  public static final String FILES = BASE_PATH + "/files";
  public static final String PRIVILEGES = BASE_PATH + "/privileges";
  public static final String ROLES = BASE_PATH + "/roles";
  public static final String USERS = BASE_PATH + "/users";
  public static final String AUDIT_LOGS = BASE_PATH + "/audit-logs";

  public static final String APPEND_ID = "/{id}";
  public static final String DOWNLOAD = APPEND_ID + "/download";
}
