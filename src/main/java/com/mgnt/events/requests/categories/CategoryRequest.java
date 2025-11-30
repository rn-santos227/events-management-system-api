package com.mgnt.events.requests.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest (
  @NotBlank(message = "Name is required") @Size(max = 255, message = "Name is too long") String name,
  @Size(max = 1024, message = "Description is too long") String description
) {}
