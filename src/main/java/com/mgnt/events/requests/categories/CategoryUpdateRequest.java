package com.mgnt.events.requests.categories;

import org.springframework.lang.Nullable;

public record CategoryUpdateRequest(
  @Nullable String name,
  @Nullable String description
) {}
