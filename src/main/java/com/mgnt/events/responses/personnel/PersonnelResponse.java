package com.mgnt.events.responses.personnel;

import java.time.LocalDateTime;
import java.util.UUID;

public record PersonnelResponse(
  UUID id,
  String name,
  String contactNumber,
  String email,
  String role,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
