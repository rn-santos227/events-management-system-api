package com.mgnt.events.responses.privileges;

import java.util.UUID;

public record PrivilegeSummary(
  UUID id,
  String name,
  String action,
  String resource,
  boolean active
) {}
