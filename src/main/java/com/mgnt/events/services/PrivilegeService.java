package com.mgnt.events.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.Privilege;
import com.mgnt.events.repositories.PrivilegeRepository;
import com.mgnt.events.requests.privileges.PrivilegeRequest;
import com.mgnt.events.responses.privileges.PrivilegeResponse;

@Service
public class PrivilegeService {
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final PrivilegeRepository privilegeRepository;

  public PrivilegeService(PrivilegeRepository privilegeRepository) {
    this.privilegeRepository = privilegeRepository;
  }

  @Transactional(readOnly = true)
  public List<PrivilegeResponse> findAll() {
    return privilegeRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public PrivilegeResponse findById(Long id) {
    return toResponse(getPrivilege(id));
  }

  @Transactional
  public PrivilegeResponse create(PrivilegeRequest request) {
    validateUniqueness(request.name(), request.action(), null);

    Privilege privilege = new Privilege(request.name(), request.action(), request.resource());
    return toResponse(privilegeRepository.save(privilege));
  }

  @Transactional
  public PrivilegeResponse update(Long id, PrivilegeRequest request) {
    Privilege privilege = getPrivilege(id);
    validateUniqueness(request.name(), request.action(), id);

    privilege.setName(request.name());
    privilege.setAction(request.action());
    privilege.setResource(request.resource());

    return toResponse(privilegeRepository.save(privilege));
  }

  @Transactional
  public void delete(Long id) {
    Privilege privilege = getPrivilege(id);
    try {

    } catch(IllegalStateException exception) {

    } catch(DataIntegrityViolationException exception) {
      
    }
  }

  private Privilege getPrivilege(Long id) {
    return privilegeRepository
      .findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilege not found"));
  }

  private void validateUniqueness(String name, String action, Long excludeId) {
    privilegeRepository
      .findByNameIgnoreCase(name)
      .filter(existing -> !existing.getId().equals(excludeId))
      .ifPresent(existing -> {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Privilege name already exists");
      });

    privilegeRepository
      .findByActionIgnoreCase(action)
      .filter(existing -> !existing.getId().equals(excludeId))
      .ifPresent(existing -> {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Privilege action already exists");
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
