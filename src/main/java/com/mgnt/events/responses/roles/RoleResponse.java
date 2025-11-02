package com.mgnt.events.responses.roles;

import java.time.LocalDateTime;
import java.util.Set;

import com.mgnt.events.responses.privileges.PrivilegeSummary;

public record RoleResponse(
  Long id,
  String name,
  Set<PrivilegeSummary> privileges,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
