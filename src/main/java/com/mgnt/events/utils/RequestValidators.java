package com.mgnt.events.utils;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public final class RequestValidators {
  private RequestValidators() {}

  @NonNull
  public static <T> T requireNonNull(@Nullable T value, String attributeName) {
    return requireNonNull(value, attributeName, HttpStatus.BAD_REQUEST);
  }
  
  @NonNull
  public static <T> T requireNonNull(
    @Nullable T value,
    String attributeName,
    HttpStatus status
  ) {
    if (status == null) {
      throw new IllegalArgumentException("HttpStatusCode must not be null");
    }
    if (value == null) {
      throw new ResponseStatusException(
        status,
        String.format("%s must not be null", attributeName)
      );
    }
    return value;
  }
}
