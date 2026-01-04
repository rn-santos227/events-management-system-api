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
  DensityOption density
) {} 
