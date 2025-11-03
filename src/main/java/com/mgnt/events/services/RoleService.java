package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import com.mgnt.events.constants.Attributes;

public class RoleService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
}
