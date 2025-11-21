package com.mgnt.events.responses.audit;

public record AuditLogUserResponse(
  Long id,
  String email,
  String fullName
) {}
