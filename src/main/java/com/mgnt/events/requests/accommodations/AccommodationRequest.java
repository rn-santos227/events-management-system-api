package com.mgnt.events.requests.accommodations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccommodationRequest(
  @NotBlank(message = "Name is required") @Size(max = 255, message = "Name is too long") String name
) {}
