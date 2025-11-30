package com.mgnt.events.requests.personnel;

import jakarta.validation.constraints.Size;

public record  PersonnelRequest(
  @NotBlank(message = "Name is required") @Size(max = 255, message = "Name is too long") String name
) {}
