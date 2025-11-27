package com.mgnt.events.responses.accomdations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.mgnt.events.enums.AccommodationType;
import com.mgnt.events.responses.files.StoredFileSummary;

public record AccommodationResponse(
  UUID id,
  String name,
  String address,
  String contactPerson
) {}
