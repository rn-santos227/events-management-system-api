package com.mgnt.events.constants;

import com.mgnt.events.enums.DensityOption;
import com.mgnt.events.enums.FontSizeOption;
import com.mgnt.events.enums.ThemeOption;

public class Defaults {
  Defaults() {}

  public static final int DEFAULT_BUCKET_LENGTH = 100;
  public static final int DEFAULT_CONTENT_TYPE_LENGTH = 100;
  public static final int DEFAULT_EMAIL_LENGTH = 100;
  public static final int DEFAULT_FILE_NAME_LENGTH = 255;
  public static final int DEFAULT_IP_LENGTH = 20;
  public static final int DEFAULT_JWT_SUBSTRING = 7;
  public static final int DEFAULT_MAX_STRING_LENGTH= 255;
  public static final int DEFAULT_METHOD_LENGTH = 20;
  public static final int DEFAULT_MID_STRING_LENGTH = 50;
  public static final int DEFAULT_PHONE_LENGTH = 20;
  public static final int DEFAULT_STORAGE_KEY_LENGTH = 512;
  public static final int DEFAULT_TOKEN_LENGTH = 512;
  public static final int DEFAULT_TEXT_SIZE = 1024;
  public static final int DEFAULT_URL_LENGTH = 2048;
  public static final int DEFAULT_PAGE_SIZE = 25;

  public static final ThemeOption DEFAULT_THEME = ThemeOption.SYSTEM;
  public static final DensityOption DEFAULT_DENSITY = DensityOption.NORMAL;
  public static final FontSizeOption DEFAULT_FONT_SIZE = FontSizeOption.MD;

  public static final String DEG_90_POSITIVE = "90.0";
  public static final String DEG_90_NEGATIVE = "-90.0";
  public static final String DEG_180_POSITIVE = "180.0";
  public static final String DEG_180_NEGATIVE = "-180.0";
}
