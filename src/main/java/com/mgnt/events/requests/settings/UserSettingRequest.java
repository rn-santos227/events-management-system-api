package com.mgnt.events.requests.settings;

import com.mgnt.events.enums.DensityOption;
import com.mgnt.events.enums.FontSizeOption;
import com.mgnt.events.enums.ThemeOption;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record  UserSettingRequest(
  @NotNull(message = "Theme is required")
  ThemeOption theme,
  @NotNull(message = "Density is required")
  DensityOption density,
  @NotNull(message = "Font size is required")
  FontSizeOption fontSize,
  @NotNull(message = "Default page size is required")
  @Min(value = 1, message = "Default page size must be at least 1")
  Integer defaultPageSize,
  @NotNull(message = "Remember state is required")
  Boolean rememberState
) {} 
