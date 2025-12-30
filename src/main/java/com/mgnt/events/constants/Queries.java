package com.mgnt.events.constants;

public class Queries {
  Queries() {}
  public static final String DELETE_RESTRICTION = "deleted_at IS NULL";
  public static final String DELETE_PRIVILEGES =
    "UPDATE privileges SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  public static final String DELETE_CATEGORIES =
    "UPDATE categories SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  public static final String DELETE_PERSONNEL =
    "UPDATE personnel SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  public static final String DELETE_VEHICLES =
    "UPDATE vehicles SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  public static final String DELETE_VENUES =
    "UPDATE venues SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  public static final String DELETE_ACCOMMODATIONS =
    "UPDATE accommodations SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  public static final String DELETE_ROLES =
    "UPDATE roles SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  public static final String DELETE_USERS =
    "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  public static final String DELETE_STORED_FILES =
    "UPDATE stored_files SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  
  public static final String ACTION = "action";
  public static final String ACTIONS = "actions";
  public static final String ACTIVITIES = "activities";
  public static final String METHOD = "method";
  public static final String PATH = "path";
  public static final String STATUS_CODE = "statusCode";
  public static final String IP_ADDRESS = "ipAddress";
  public static final String MESSAGE = "message";
  public static final String USER = "user";
  public static final String USER_ID = "userId";
  public static final String START_DATE = "startDate";
  public static final String END_DATE = "endDate";

  public static final String ID = "id";
  public static final String LIMIT = "limit";
  public static final String PAGE = "page";
  public static final String CREATED_AT = "createdAt";

  public static final String TEXT = "TEXT";
}
