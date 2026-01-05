package com.mgnt.events.requests.privileges;

import org.springframework.lang.Nullable;

public record PrivilegeUpdateRequest(
  @Nullable String name,
  @Nullable String action,
  @Nullable String resource,
  @Nullable Boolean active
) {}
