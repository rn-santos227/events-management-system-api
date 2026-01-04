package com.mgnt.events.services;

import static com.mgnt.events.constants.Cache.KEY_ALL;
import static com.mgnt.events.constants.Cache.KEY_ID;
import static com.mgnt.events.constants.Cache.PRIVILEGES;
import static com.mgnt.events.constants.Cache.PRIVILEGE_BY_ID;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import com.mgnt.events.util.RequestValidators;

@Service
public class PrivilegeService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);
  private final PrivilegeRepository _privilegeRepository;

  public PrivilegeService(PrivilegeRepository privilegeRepository) {
    this._privilegeRepository = privilegeRepository;
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = PRIVILEGES, key = KEY_ALL)
  public List<PrivilegeResponse> findAll(@Nullable Integer limit) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    if (sanitizedLimit == null) {
      return _privilegeRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
    }
    return _privilegeRepository
      .findAll(PageRequest.of(0, sanitizedLimit, DEFAULT_SORT))
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = PRIVILEGE_BY_ID, key = KEY_ID)
  public PrivilegeResponse findById(@NonNull UUID id) {
    return toResponse(getPrivilege(id));
  }

  @Transactional(rollbackFor = Throwable.class)
  @CacheEvict(cacheNames = { PRIVILEGES, PRIVILEGE_BY_ID }, allEntries = true)
  public PrivilegeResponse update(@NonNull UUID id, PrivilegeRequest request) {
    Privilege privilege = getPrivilege(id);
    validateUniqueness(request.name(), request.action(), id);

    privilege.setName(request.name());
    privilege.setAction(request.action());
    privilege.setResource(request.resource());

    return toResponse(_privilegeRepository.save(privilege));
  }

  private Privilege getPrivilege(@NonNull UUID id) {
    return _privilegeRepository
      .findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilege not found"));
  }

  private void validateUniqueness(String name, String action, UUID excludeId) {
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
