package com.mgnt.events.requests.categories;

import com.mgnt.events.constants.Defaults;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest (
  @NotBlank(message = "Name is required") @Size(max = Defaults.DEFAULT_MAX_STRING_LENGTH, message = "Name is too long") String name,
  @Size(max = Defaults.DEFAULT_TEXT_SIZE, message = "Description is too long") String description
) {}
