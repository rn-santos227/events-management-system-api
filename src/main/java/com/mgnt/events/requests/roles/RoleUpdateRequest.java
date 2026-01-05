package com.mgnt.events.requests.roles;

import java.util.Set;
import java.util.UUID;
import org.springframework.lang.Nullable;

public record RoleUpdateRequest(
  @Nullable String name,
  @Nullable Set<UUID> privilegeIds
) {}
