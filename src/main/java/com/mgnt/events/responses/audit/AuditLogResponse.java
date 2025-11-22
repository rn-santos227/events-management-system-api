package com.mgnt.events.responses.audit;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuditLogResponse(
  UUID id,
  String action,
  String method,
  String path,
  int statusCode,
  String ipAddress,
  String message,
  AuditLogUserResponse user,
  LocalDateTime createdAt
) {}
