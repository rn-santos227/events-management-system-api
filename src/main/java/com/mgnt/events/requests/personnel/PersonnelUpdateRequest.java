package com.mgnt.events.requests.personnel;

import org.springframework.lang.Nullable;

public record PersonnelUpdateRequest(
  @Nullable String fullName,
  @Nullable String contactNumber,
  @Nullable String email,
  @Nullable String role
) {}
