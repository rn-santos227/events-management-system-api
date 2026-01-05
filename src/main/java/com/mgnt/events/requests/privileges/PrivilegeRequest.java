package com.mgnt.events.requests.privileges;

import jakarta.validation.constraints.NotBlank;

public record PrivilegeRequest(
  @NotBlank(message = "Name is required") String name,
  @NotBlank(message = "Action is required") String action,
  @NotBlank(message = "Resource is required") String resource,
  Boolean active
) {}
