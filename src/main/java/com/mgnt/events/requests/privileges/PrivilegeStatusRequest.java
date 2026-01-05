package com.mgnt.events.requests.privileges;

import jakarta.validation.constraints.NotNull;

public record PrivilegeStatusRequest(
  @NotNull(message = "Active status is required") Boolean active
) {}
