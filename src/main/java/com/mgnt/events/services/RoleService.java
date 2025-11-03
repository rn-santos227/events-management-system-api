package com.mgnt.events.services;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

  @Transactional(readOnly = true)
  public List<RoleResponse> findAll() {
    return roleRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public RoleResponse findById(Long id) {
    Role role = roleRepository
      .findWithPrivilegesById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
    return toResponse(role);
  }

  private Role getRole(Long id) {
    return roleRepository
      .findWithPrivilegesById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
  }

  private void validateNameUniqueness(String name, Long excludeId) {
    roleRepository
      .findByNameIgnoreCase(name)
      .filter(existing -> !existing.getId().equals(excludeId))
      .ifPresent(existing -> {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Role name already exists");
      });
  }


  private Set<Privilege> resolvePrivileges(Set<Long> privilegeIds) {
    if (privilegeIds == null || privilegeIds.isEmpty()) {
      return new LinkedHashSet<>();
    }

    List<Privilege> privileges = privilegeRepository.findAllById(privilegeIds);
    Set<Long> foundIds = privileges.stream().map(Privilege::getId).collect(Collectors.toSet());
    List<Long> missing = privilegeIds
      .stream()
      .filter(id -> !foundIds.contains(id))
      .toList();

    if (!missing.isEmpty()) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Unknown privilege identifiers: " + missing
      );
    }

    return new LinkedHashSet<>(privileges);
  }

  private RoleResponse toResponse(Role role) {
    Set<PrivilegeSummary> privilegeSummaries;
    if (role.getPrivileges() == null) {
      privilegeSummaries = new LinkedHashSet<>();
    } else {
      Comparator<Privilege> comparator = Comparator.comparing(
        Privilege::getName,
        Comparator.nullsLast((String.CASE_INSENSITIVE_ORDER))
      );
      privilegeSummaries = role
        .getPrivileges()
        .stream()
        .sorted(comparator)
        .map(this::toPrivilegeSummary)
        .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    return new RoleResponse(
      role.getId(),
      role.getName(),
      privilegeSummaries,
      role.getCreatedAt(),
      role.getUpdatedAt()
    );
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
