package com.mgnt.events.responses.settings;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mgnt.events.enums.DensityOption;
import com.mgnt.events.enums.FontSizeOption;
import com.mgnt.events.enums.ThemeOption;

public record UserSettingResponse(
  UUID userId,
  ThemeOption theme,
  DensityOption density,
  FontSizeOption fontSize,
  Integer defaultPageSize,
  boolean rememberState,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
