package com.mgnt.events.constants;

public final class Cache {
  private Cache() {}

  public static final String ACCOMMODATIONS = "accommodations";
  public static final String ACCOMMODATION_BY_ID = "accommodation-by-id";
  public static final String AUDIT_LOGS = "audit-logs";
  public static final String AUDIT_LOGS_BY_USER = "audit-logs-by-user";
  public static final String CATEGORIES = "categories";
  public static final String CATEGORY_BY_ID = "category-by-id";
  public static final String PERSONNEL = "personnel";
  public static final String PERSONNEL_BY_ID = "personnel-by-id";
  public static final String PRIVILEGES = "privileges";
  public static final String PRIVILEGE_BY_ID = "privilege-by-id";
  public static final String ROLES = "roles";
  public static final String ROLE_BY_ID = "role-by-id";
  public static final String USERS = "users";
  public static final String USER_BY_ID = "user-by-id";
  public static final String VEHICLES = "vehicles";
  public static final String VEHICLE_BY_ID = "vehicle-by-id";
  public static final String VENUES = "venues";
  public static final String VENUE_BY_ID = "venue-by-id";

  public static final String KEY = "T(String).format('%s:%s', #limit?:'all', #page?:'all')";
  public static final String KEY_ALL = "#limit?:'all'";
  public static final String KEY_ID = "#id";
  public static final String KEY_USER = "T(String).format('%s:%s', T(java.util.Objects).requireNonNull(#root.target.requireUser()).getId(), #limit?:'all')";
}
