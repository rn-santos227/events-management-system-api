package com.mgnt.events.responses.users;

import java.time.LocalDateTime;

import com.mgnt.events.responses.roles.RoleSummary;

public record UserResponse(
  Long id,
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
