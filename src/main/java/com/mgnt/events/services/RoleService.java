package com.mgnt.events.services;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.Privilege;
import com.mgnt.events.models.Role;
import com.mgnt.events.repositories.PrivilegeRepository;
import com.mgnt.events.repositories.RoleRepository;
import com.mgnt.events.responses.privileges.PrivilegeSummary;
import com.mgnt.events.responses.roles.RoleResponse;

public class RoleService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final RoleRepository roleRepository;
  private final PrivilegeRepository privilegeRepository;

  public RoleService(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
    this.roleRepository = roleRepository;
    this.privilegeRepository = privilegeRepository;
  }


  private RoleResponse toResponse(Role role) {
    Set<PrivilegeSummary> privilegeSummaries;
    if(role.getPrivileges() == null) {
      privilegeSummaries = new LinkedHashSet<>();
    } else {
      Comparator<Privilege> comparator = Comparator.comparing(
        Privilege::getName,
        Comparator.nullsLast((String.CASE_INSENSITIVE_ORDER))
      );
    }
  }

  private PrivilegeSummary toPrivilegeSummary(Privilege privilege) {
    return new PrivilegeSummary(
      privilege.getId(),
      privilege.getName(),
      privilege.getAction(),
      privilege.getResource()
    );
  }
}
