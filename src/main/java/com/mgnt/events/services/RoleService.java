package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.repositories.PrivilegeRepository;
import com.mgnt.events.repositories.RoleRepository;

public class RoleService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final RoleRepository roleRepository;
  private final PrivilegeRepository privilegeRepository;

  public RoleService(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
    this.roleRepository = roleRepository;
    this.privilegeRepository = privilegeRepository;
  }
}
