package com.mgnt.events.responses.roles;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.mgnt.events.responses.privileges.PrivilegeSummary;

public record RoleResponse(
  UUID id,
  String name,
  Set<PrivilegeSummary> privileges,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
