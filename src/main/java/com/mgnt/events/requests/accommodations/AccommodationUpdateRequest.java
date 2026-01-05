package com.mgnt.events.requests.accommodations;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.lang.Nullable;

import com.mgnt.events.enums.AccommodationType;

public record AccommodationUpdateRequest(
  @Nullable String name,
  @Nullable String address,
  @Nullable String contactPerson,
  @Nullable String contactNumber,
  @Nullable String email,
  @Nullable AccommodationType type,
  @Nullable BigDecimal latitude,
  @Nullable BigDecimal longitude,
  @Nullable UUID imageId
) {}
