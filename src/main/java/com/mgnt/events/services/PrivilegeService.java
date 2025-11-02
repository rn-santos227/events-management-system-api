package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.Privilege;
import com.mgnt.events.repositories.PrivilegeRepository;
import com.mgnt.events.responses.privileges.PrivilegeResponse;

@Service
public class PrivilegeService {
  private static final Sort DEFA_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final PrivilegeRepository privilegeRepository;

  public PrivilegeService(PrivilegeRepository privilegeRepository) {
    this.privilegeRepository = privilegeRepository;
  }

  private void validateUniqueness(String name, String action, Long excludeId) {
    privilegeRepository
      .findByNameIgnoreCase(name)
      .filter(existing -> !existing.getId().equals(excludeId))
      .ifPresent(existing -> {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Privilege name already exists");
      });
  }

  private PrivilegeResponse toResponse(Privilege privilege) {
    return new PrivilegeResponse(
      privilege.getId(),
      privilege.getName(),
      privilege.getAction(),
      privilege.getResource(),
      privilege.getCreatedAt(),
      privilege.getUpdatedAt()
    );
  }
}
