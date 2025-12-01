package com.mgnt.events.requests.accommodations;

import com.mgnt.events.constants.Defaults;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccommodationRequest(
  @NotBlank(message = "Name is required") @Size(max = Defaults.DEFAULT_MAX_STRING_LENGTH, message = "Name is too long") String name,
  @NotBlank(message = "Address is required") String address,
  @NotBlank(message = "Contact person is required") @Size(max = Defaults.DEFAULT_MAX_STRING_LENGTH, message = "Contact person is too long") String contactPerson,
  @Size(max = Defaults.DEFAULT_CONTENT_TYPE_LENGTH, message = "Contact number is too long") String contactNumber
) {}
