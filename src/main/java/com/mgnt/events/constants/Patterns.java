package com.mgnt.events.constants;

public class Patterns {
  Patterns() {}

  public static final String AWS_PATTERN = "https://%s.s3.%s.amazonaws.com/%s";
  public static final String ATTACHMENTS= "attachment; filename=\"%s\"";
  public static final String DATE_FORMAT = "yyyy/MM/dd";
  public static final String STORAGE_PATTERN = "%s/%s/%s";
  public static final String ROUTES_PATTERN = "%-6s %-8s %-40s %s%n";
  public static final String GRAPHQL_ROUTE_PATTERN = "POST   /graphql   %s(%s): %s";
  public static final String GRAPHQL_TYPE_PATTERN = "%s: %s";
}
