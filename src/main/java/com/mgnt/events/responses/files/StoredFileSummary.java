package com.mgnt.events.responses.files;

import java.util.UUID;

public record StoredFileSummary(
  UUID id,
  String fileName,
  String url
) {}
