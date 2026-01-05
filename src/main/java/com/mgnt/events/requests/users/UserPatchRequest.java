package com.mgnt.events.requests.users;

import java.util.UUID;
import org.springframework.lang.Nullable;

public record UserPatchRequest(
  @Nullable String email,
  @Nullable String password,
  @Nullable String firstName,
  @Nullable String lastName,
  @Nullable String contactNumber,
  @Nullable UUID roleId,
  @Nullable Boolean active
) {}
