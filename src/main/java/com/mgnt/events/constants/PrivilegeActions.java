package com.mgnt.events.constants;

public final class PrivilegeActions {
  private PrivilegeActions() {}

  public static final String AUDIT_LOGS_READ = "audit_logs:read";
  public static final String AUDIT_LOGS_READ_OWN = "audit_logs:read:own";
  public static final String USERS_READ = "users:read";
  public static final String ROLES_READ = "roles:read";
  public static final String PRIVILEGES_READ = "privileges:read";
  public static final String STATUS_READ = "status:read";
  public static final String TEST_READ = "test:read";
  public static final String FILES_READ = "files:read";
  public static final String FILES_DOWNLOAD = "files:download";
  public static final String FILES_UPLOAD = "files:upload";
  public static final String FILES_DELETE = "files:delete";

  public static final String CATEGORIES_CREATE = "categories:create";
  public static final String CATEGORIES_READ = "categories:read";
  public static final String CATEGORIES_UPDATE = "categories:update";
  public static final String CATEGORIES_DELETE = "categories:delete";

  public static final String PERSONNEL_CREATE = "personnel:create";
  public static final String PERSONNEL_READ = "personnel:read";
  public static final String PERSONNEL_UPDATE = "personnel:update";
  public static final String PERSONNEL_DELETE = "personnel:delete";


}
