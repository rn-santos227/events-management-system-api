package com.mgnt.events.responses.users;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mgnt.events.responses.roles.RoleSummary;

public record UserResponse(
  UUID id,
  String email,
  String firstName,
  String lastName,
  String contactNumber,
  boolean active,
  RoleSummary role,
  LocalDateTime createdAt,
  LocalDateTime updatedAt,
  LocalDateTime deletedAt
) {}
