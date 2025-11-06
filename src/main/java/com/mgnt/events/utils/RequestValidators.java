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

    if (value instanceof CharSequence sequence) {
      return sequence.toString().trim().isEmpty();
    }

    if (value instanceof Optional<?> optional) {
      return optional.isEmpty() || isBlank(optional.orElse(null));
    }

    if (value instanceof Collection<?> collection) {
      if (collection.isEmpty()) {
        return true;
      }
      for (Object element : collection) {
        if (!isBlank(element)) {
          return false;
        }
      }
      return true;
    }

    if (value instanceof Map<?, ?> map) {
      if (map.isEmpty()) {
        return true;
      }
      for (Object entryValue : map.values()) {
        if (!isBlank(entryValue)) {
          return false;
        }
      }
      return true;
    }

    if (value.getClass().isArray()) {
      int length = Array.getLength(value);
      if (length == 0) {
        return true;
      }
      for (int index = 0; index < length; index++) {
        if (!isBlank(Array.get(value, index))) {
          return false;
        }
      }
      return true;
    }

    return false;
  }
}
