package com.mgnt.events.responses.audit;


import java.time.LocalDateTime;

public record AuditLogResponse(
  Long id,
  String action,
  String method,
  String path,
  int statusCode,
  String ipAddress,
  String message,
  AuditLogUserResponse user,
  LocalDateTime createdAt
) {}
