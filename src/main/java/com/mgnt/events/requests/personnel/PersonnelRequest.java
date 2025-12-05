package com.mgnt.events.requests.personnel;

import com.mgnt.events.constants.Defaults;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record  PersonnelRequest(
  @NotBlank(message = "Name is required") @Size(max = Defaults.DEFAULT_MAX_STRING_LENGTH, message = "Name is too long") String name,
  @NotBlank(message = "Contact number is required") @Size(max = Defaults.DEFAULT_PHONE_LENGTH, message = "Contact number is too long") String contactNumber,
  @Email(message = "Invalid email") @Size(max = Defaults.DEFAULT_EMAIL_LENGTH, message = "Email is too long") String email,
  @Size(max = Defaults.DEFAULT_MAX_STRING_LENGTH, message = "Role is too long") String function
) {}
