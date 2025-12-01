package com.mgnt.events.requests.venues;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

import com.mgnt.events.constants.Defaults;
import com.mgnt.events.enums.VenueType;

public record VenueRequest(
  @NotBlank(message = "Name is required") @Size(max = Defaults.DEFAULT_MAX_STRING_LENGTH, message = "Name is too long") String name,
  @NotBlank(message = "Address is required") String address,
  @NotBlank(message = "Contact person is required") @Size(max = Defaults.DEFAULT_MAX_STRING_LENGTH, message = "Contact person is too long") String contactPerson,
  @Size(max = Defaults.DEFAULT_PHONE_LENGTH, message = "Contact number is too long") String contactNumber,
  @Email(message = "Invalid email") @Size(max = 100, message = "Email is too long") String email,
  @NotNull(message = "Type is required") VenueType type,
  @DecimalMin(value = Defaults.DEG_90_NEGATIVE, message = "Latitude must be greater than or equal to -90")
  @DecimalMax(value = Defaults.DEG_90_POSITIVE, message = "Latitude must be less than or equal to 90")
  BigDecimal latitude,
  @DecimalMin(value = Defaults.DEG_180_NEGATIVE, message = "Longitude must be greater than or equal to -180")
  @DecimalMax(value = Defaults.DEG_180_POSITIVE, message = "Longitude must be less than or equal to 180")
  BigDecimal longitude,
  UUID imageId
) {}