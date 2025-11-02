package com.mgnt.events.responses.privileges;

import java.time.LocalDateTime;

public record PrivilegeResponse(
  Long id,
  String name,
  String action,
  String resource,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
