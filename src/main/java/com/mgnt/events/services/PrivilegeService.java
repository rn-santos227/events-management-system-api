package com.mgnt.events.services;

import java.util.List;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.models.Privilege;
import com.mgnt.events.repositories.PrivilegeRepository;
import com.mgnt.events.requests.privileges.PrivilegeRequest;
import com.mgnt.events.responses.privileges.PrivilegeResponse;
import com.mgnt.events.utils.RequestValidators;

@Service
public class PrivilegeService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final PrivilegeRepository _privilegeRepository;

  public PrivilegeService(PrivilegeRepository privilegeRepository) {
    this._privilegeRepository = privilegeRepository;
  }

  @Transactional(readOnly = true)
  public List<PrivilegeResponse> findAll(@Nullable Integer limit) {
    if (limit == null) {
      return _privilegeRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
    }

    int sanitizedLimit = RequestValidators.requirePositive(limit, Queries.LIMIT);
    return _privilegeRepository
      .findAll(PageRequest.of(0, sanitizedLimit, DEFAULT_SORT))
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @Transactional(readOnly = true)
  public PrivilegeResponse findById(@NonNull Long id) {
    return toResponse(getPrivilege(id));
  }

  @Transactional
  public PrivilegeResponse create(PrivilegeRequest request) {
    validateUniqueness(request.name(), request.action(), null);

    Privilege privilege = new Privilege(request.name(), request.action(), request.resource());
    return toResponse(_privilegeRepository.save(privilege));
  }

  @Transactional
  public PrivilegeResponse update(@NonNull Long id, PrivilegeRequest request) {
    Privilege privilege = getPrivilege(id);
    validateUniqueness(request.name(), request.action(), id);

    privilege.setName(request.name());
    privilege.setAction(request.action());
    privilege.setResource(request.resource());

    return toResponse(_privilegeRepository.save(privilege));
  }

  @Transactional
  public void delete(@NonNull Long id) {
    Privilege privilege = Objects.requireNonNull(getPrivilege(id));
    try {
      _privilegeRepository.delete(privilege);
    } catch (IllegalStateException exception) {
      throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        exception.getMessage() != null ? exception.getMessage() : "Unable to delete privilege",
        exception
      );
    } catch (DataIntegrityViolationException exception) {
      throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        "Unable to delete privilege while it is still in use",
        exception
      );
    }
  }

  private Privilege getPrivilege(@NonNull Long id) {
    return _privilegeRepository
      .findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilege not found"));
  }

  private void validateUniqueness(String name, String action, Long excludeId) {
    _privilegeRepository
      .findByNameIgnoreCase(name)
      .filter(existing -> !existing.getId().equals(excludeId))
      .ifPresent(existing -> {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Privilege name already exists");
      });

    _privilegeRepository
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
