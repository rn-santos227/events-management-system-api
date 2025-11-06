package com.mgnt.events.services;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.Privilege;
import com.mgnt.events.models.Role;
import com.mgnt.events.repositories.PrivilegeRepository;
import com.mgnt.events.repositories.RoleRepository;
import com.mgnt.events.requests.roles.RoleRequest;
import com.mgnt.events.responses.privileges.PrivilegeSummary;
import com.mgnt.events.responses.roles.RoleResponse;

@Service
public class RoleService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final RoleRepository _roleRepository;
  private final PrivilegeRepository _privilegeRepository;

  public RoleService(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
    this._roleRepository = roleRepository;
    this._privilegeRepository = privilegeRepository;
  }

  @Transactional(readOnly = true)
  public List<RoleResponse> findAll(@Nullable Integer limit) {
    if (limit == null) {
      return _roleRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
    }
  }

  @Transactional(readOnly = true)
  public RoleResponse findById(Long id) {
    Role role = _roleRepository
      .findWithPrivilegesById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
    return toResponse(Objects.requireNonNull(role));
  }

  @Transactional
  public RoleResponse create(RoleRequest request) {
    validateNameUniqueness(request.name(), null);
    Role role = new Role(request.name());
    role.setPrivileges(resolvePrivileges(request.privilegeIds()));
    return toResponse(_roleRepository.save(role));
  }

  @Transactional
  public RoleResponse update(@NonNull Long id, RoleRequest request) {
    Role role = Objects.requireNonNull(getRole(id));
    validateNameUniqueness(request.name(), id);

    role.setName(request.name());
    role.setPrivileges(resolvePrivileges(request.privilegeIds()));

    return toResponse(_roleRepository.save(role));
  }

  @Transactional
  public void delete(@NonNull Long id) {
    Role role = Objects.requireNonNull(getRole(id));
    try {
      _roleRepository.delete(role);
    } catch (IllegalStateException exception) {
      throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        exception.getMessage() != null ? exception.getMessage() : "Unable to delete role",
        exception
      );
    } catch (DataIntegrityViolationException exception) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Unable to delete role", exception);
    }
  }

  private Role getRole(@NonNull Long id) {
    return Objects.requireNonNull(
      _roleRepository
        .findWithPrivilegesById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"))
    );
  }

  private void validateNameUniqueness(String name, Long excludeId) {
    _roleRepository
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

    List<Privilege> privileges = _privilegeRepository
      .findAllById(privilegeIds)
      .stream()
      .map(privilege -> Objects.requireNonNull(privilege, "Privilege must not be null"))
      .toList();
    Set<Long> foundIds = privileges
      .stream()
      .map(privilege -> Objects.requireNonNull(privilege.getId(), "Privilege identifier must not be null"))
      .collect(Collectors.toSet());
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
    Role ensuredRole = Objects.requireNonNull(role, "Role must not be null");
    Set<PrivilegeSummary> privilegeSummaries;
    if (ensuredRole.getPrivileges() == null) {
      privilegeSummaries = new LinkedHashSet<>();
    } else {
      Comparator<Privilege> comparator = Comparator.comparing(
        Privilege::getName,
        Comparator.nullsLast((String.CASE_INSENSITIVE_ORDER))
      );
      privilegeSummaries = ensuredRole
        .getPrivileges()
        .stream()
        .filter(Objects::nonNull)
        .sorted(comparator)
        .map(this::toPrivilegeSummary)
        .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    return new RoleResponse(
      Objects.requireNonNull(ensuredRole.getId(), "Role identifier must not be null"),
      ensuredRole.getName(),
      privilegeSummaries,
      ensuredRole.getCreatedAt(),
      ensuredRole.getUpdatedAt()
    );
  }

  private PrivilegeSummary toPrivilegeSummary(Privilege privilege) {
    Privilege ensuredPrivilege = Objects.requireNonNull(privilege, "Privilege must not be null");
    return new PrivilegeSummary(
      Objects.requireNonNull(ensuredPrivilege.getId(), "Privilege identifier must not be null"),
      ensuredPrivilege.getName(),
      ensuredPrivilege.getAction(),
      ensuredPrivilege.getResource()
    );
  }
}
