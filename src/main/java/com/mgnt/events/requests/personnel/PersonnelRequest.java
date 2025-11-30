package com.mgnt.events.requests.personnel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record  PersonnelRequest(
  @NotBlank(message = "Name is required") @Size(max = 255, message = "Name is too long") String name,
  @NotBlank(message = "Contact number is required") @Size(max = 20, message = "Contact number is too long") String contactNumber
) {}
