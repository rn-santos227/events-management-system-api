package com.mgnt.events.responses.vehicles;

import java.util.UUID;

public record VehiclePersonnelSummary(
  UUID id,
  String name,
  String contactNumber,
  String function
) {}
