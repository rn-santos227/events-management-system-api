package com.mgnt.events.requests.vehicles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

import com.mgnt.events.enums.VehicleStatus;
import com.mgnt.events.enums.VehicleType;

public record VehicleRequest(
  @NotBlank(message = "Name is required") @Size(max = 255, message = "Name is too long") String name
) {}
