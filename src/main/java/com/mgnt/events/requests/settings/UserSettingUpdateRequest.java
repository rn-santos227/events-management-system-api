package com.mgnt.events.requests.settings;

import org.springframework.lang.Nullable;

import com.mgnt.events.enums.DensityOption;
import com.mgnt.events.enums.FontSizeOption;
import com.mgnt.events.enums.ThemeOption;

public record UserSettingUpdateRequest(
  @Nullable ThemeOption theme,
  @Nullable DensityOption density,
  @Nullable FontSizeOption fontSize,
  @Nullable Integer defaultPageSize,
  @Nullable Boolean rememberState
) {}
