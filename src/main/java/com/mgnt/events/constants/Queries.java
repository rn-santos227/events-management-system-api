package com.mgnt.events.constants;

public class Queries {
  Queries() {}
  public static final String DELETE_RESTRICTION = "deleted_at IS NULL";
  public static final String DELETE_PRIVILEGES =
    "UPDATE privileges SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  public static final String DELETE_ROLES =
    "UPDATE roles SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  public static final String DELETE_USERS =
    "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
  public static final String LIMIT = "limit";
}
