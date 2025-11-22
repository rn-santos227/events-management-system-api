package com.mgnt.events.requests.roles;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import java.util.UUID;

public record RoleRequest(
  @NotBlank(message = "Name is required") String name,
  Set<UUID> privilegeIds
) {}
