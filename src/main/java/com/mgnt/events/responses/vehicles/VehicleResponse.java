package com.mgnt.events.responses.vehicles;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mgnt.events.enums.VehicleStatus;
import com.mgnt.events.enums.VehicleType;

public record VehicleResponse(
  UUID id,
  String name,
  VehicleType type,
  VehicleStatus status,
  String plateNumber,
  String contactNumber,
  VehiclePersonnelSummary assignedPersonnel,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
