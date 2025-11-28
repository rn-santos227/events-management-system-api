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
  public static final String LIMIT = "limit";
  public static final String CREATED_AT = "createdAt";
  public static final String TEXT = "TEXT";
}
