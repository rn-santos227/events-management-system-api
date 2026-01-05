package com.mgnt.events.requests.privileges;

import org.springframework.lang.Nullable;

public record PrivilegeUpdateRequest(
  @Nullable Boolean active
) {}
