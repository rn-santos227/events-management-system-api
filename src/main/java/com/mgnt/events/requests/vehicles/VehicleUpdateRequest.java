package com.mgnt.events.requests.vehicles;

import java.util.UUID;
import org.springframework.lang.Nullable;

import com.mgnt.events.enums.VehicleStatus;
import com.mgnt.events.enums.VehicleType;

public record VehicleUpdateRequest(
  @Nullable String name,
  @Nullable VehicleType type,
  @Nullable VehicleStatus status,
  @Nullable String plateNumber,
  @Nullable String contactNumber,
  @Nullable UUID assignedPersonnelId
) {}
