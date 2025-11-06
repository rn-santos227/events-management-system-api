package com.mgnt.events.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
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

  public static int requirePositive(Integer value, String attributeName) {
    return requirePositive(value, attributeName, HttpStatus.BAD_REQUEST);
  }

  @Nullable
  public static Integer requirePositiveOrNull(@Nullable Integer value, String attributeName) {
    if (value == null) {
      return null;
    }
    return requirePositive(value, attributeName);
  }

  public static int requirePositive(Integer value, String attributeName, HttpStatus status) {
    if (status == null) {
      throw new IllegalArgumentException("HttpStatusCode must not be null");
    }

    if (value == null) {
      throw new ResponseStatusException(
        status,
        String.format("%s must not be null", attributeName)
      );
    }

    if (value.intValue() <= 0) {
      throw new ResponseStatusException(
        status,
        String.format("%s must be greater than zero", attributeName)
      );
    }
    return value;
  }

  public static boolean isBlank(@Nullable Object value) {
    if (value == null) {
      return true;
    }

    return false;
  }
}
