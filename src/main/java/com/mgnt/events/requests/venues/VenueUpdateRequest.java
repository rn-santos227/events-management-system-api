package com.mgnt.events.requests.venues;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.lang.Nullable;

import com.mgnt.events.enums.VenueType;

public record VenueUpdateRequest(
  @Nullable String name,
  @Nullable String address,
  @Nullable String contactPerson,
  @Nullable String contactNumber,
  @Nullable String email,
  @Nullable VenueType type,
  @Nullable BigDecimal latitude,
  @Nullable BigDecimal longitude,
  @Nullable UUID imageId
) {}
