package com.mgnt.events.responses.audit;

import java.util.UUID;

public record AuditLogUserResponse(
  UUID id,
  String email,
  String fullName
) {}
