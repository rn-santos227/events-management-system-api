package com.mgnt.events.responses.privileges;

import java.time.LocalDateTime;
import java.util.UUID;

public record PrivilegeResponse(
  UUID id,
  String name,
  String action,
  String resource,
  boolean active,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
