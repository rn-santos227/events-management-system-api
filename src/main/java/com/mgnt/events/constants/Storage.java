package com.mgnt.events.constants;

public class Storage {
  private Storage() {}

  public static final String PROPERTY_PREFIX = "storage";
  public static final String ENABLED_PROPERTY_NAME = "enabled";
  public static final String PROVIDER_PROPERTY_NAME = "provider";
  public static final String ENDPOINT_PROPERTY_NAME = "endpoint";
  public static final String REGION_PROPERTY_NAME = "region";
  public static final String ACCESS_KEY_PROPERTY_NAME = "access-key";
  public static final String SECRET_KEY_PROPERTY_NAME = "secret-key";
  public static final String BUCKET_PROPERTY_NAME = "bucket";
  public static final String PATH_STYLE_ACCESS_PROPERTY_NAME = "path-style-access";

  public static final String ENABLED_PROPERTY = PROPERTY_PREFIX + "." + ENABLED_PROPERTY_NAME;
  public static final String PROVIDER_PROPERTY = PROPERTY_PREFIX + "." + PROVIDER_PROPERTY_NAME;
  public static final String ENDPOINT_PROPERTY = PROPERTY_PREFIX + "." + ENDPOINT_PROPERTY_NAME;
  public static final String REGION_PROPERTY = PROPERTY_PREFIX + "." + REGION_PROPERTY_NAME;
  public static final String ACCESS_KEY_PROPERTY = PROPERTY_PREFIX + "." + ACCESS_KEY_PROPERTY_NAME;
  public static final String SECRET_KEY_PROPERTY = PROPERTY_PREFIX + "." + SECRET_KEY_PROPERTY_NAME;
  public static final String BUCKET_PROPERTY = PROPERTY_PREFIX + "." + BUCKET_PROPERTY_NAME;
  public static final String PATH_STYLE_ACCESS_PROPERTY = PROPERTY_PREFIX + "." + PATH_STYLE_ACCESS_PROPERTY_NAME;
  public static final String DEFAULT_REGION = "us-east-1";
}
