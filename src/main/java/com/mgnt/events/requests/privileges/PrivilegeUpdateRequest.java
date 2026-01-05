package com.mgnt.events.requests.privileges;

import jakarta.validation.constraints.NotNull;

public record PrivilegeUpdateRequest(
  @NotNull Boolean active
) {}
