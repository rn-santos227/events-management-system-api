package com.mgnt.events.requests.vehicles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

import com.mgnt.events.constants.Defaults;
import com.mgnt.events.enums.VehicleStatus;
import com.mgnt.events.enums.VehicleType;

public record VehicleRequest(
  @NotBlank(message = "Name is required") @Size(max = Defaults.DEFAULT_MAX_STRING_LENGTH, message = "Name is too long") String name,
  @NotNull(message = "Type is required") VehicleType type,
  @NotNull(message = "Status is required") VehicleStatus status,
  @NotBlank(message = "Plate number is required") @Size(max = 50, message = "Plate number is too long") String plateNumber,
  @NotBlank(message = "Contact number is required") @Size(max = 20, message = "Contact number is too long") String contactNumber,
  UUID assignedPersonnelId
) {}
