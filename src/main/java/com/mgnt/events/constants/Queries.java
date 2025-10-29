package com.mgnt.events.constants;

public class Queries {
  Queries() {}
  public static final String DELETE_RESTRICTION = "deleted_at IS NULL";
  public static final String DELETE_TIMESTAMP = "UPDATE privileges SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
}
