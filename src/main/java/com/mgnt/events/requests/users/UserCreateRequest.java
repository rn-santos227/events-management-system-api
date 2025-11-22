package com.mgnt.events.requests.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UserCreateRequest(
  @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,
  @NotBlank(message = "Password is required") String password,
  @NotBlank(message = "First name is required") String firstName,
  @NotBlank(message = "Last name is required") String lastName,
  @NotBlank(message = "Contact number is required") String contactNumber,
  @NotNull(message = "Role is required") UUID roleId,
  Boolean active
) {}
