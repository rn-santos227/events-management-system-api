package com.mgnt.events.responses.venues;

import com.mgnt.events.enums.VenueType;
import com.mgnt.events.responses.files.StoredFileSummary;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record VenueResponse(
  UUID id,
  String name,
  String address,
  String contactPerson,
  String contactNumber,
  String email,
  VenueType type,
  BigDecimal latitude
) {}
