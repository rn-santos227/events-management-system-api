package com.mgnt.events.requests.accommodations;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.mgnt.events.constants.Defaults;
import com.mgnt.events.enums.AccommodationType;

public record AccommodationRequest(
  @NotBlank(message = "Name is required") @Size(max = Defaults.DEFAULT_MAX_STRING_LENGTH, message = "Name is too long") String name,
  @NotBlank(message = "Address is required") String address,
  @NotBlank(message = "Contact person is required") @Size(max = Defaults.DEFAULT_MAX_STRING_LENGTH, message = "Contact person is too long") String contactPerson,
  @Size(max = Defaults.DEFAULT_CONTENT_TYPE_LENGTH, message = "Contact number is too long") String contactNumber,
  @Email(message = "Invalid email") @Size(max = Defaults.DEFAULT_EMAIL_LENGTH, message = "Email is too long") String email,
  @NotNull(message = "Type is required") AccommodationType type
) {}
