package com.mgnt.events.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
import com.mgnt.events.models.Personnel;
import com.mgnt.events.repositories.PersonnelRepository;
import com.mgnt.events.repositories.VehicleRepository;
import com.mgnt.events.requests.personnel.PersonnelRequest;
import com.mgnt.events.responses.personnel.PersonnelResponse;
import com.mgnt.events.util.RequestValidators;

@Service
public class PersonnelService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);

  private final PersonnelRepository _personnelRepository;
  private final VehicleRepository _vehicleRepository;

  public PersonnelService(
    PersonnelRepository personnelRepository,
    VehicleRepository vehicleRepository
  ) {
    this._personnelRepository = personnelRepository;
    this._vehicleRepository = vehicleRepository;
  }

  @Transactional(readOnly = true)
  public List<PersonnelResponse> findAll(@Nullable Integer limit) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    if (sanitizedLimit == null) {
      return _personnelRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
    }

   return _personnelRepository
      .findAll(PageRequest.of(0, sanitizedLimit, DEFAULT_SORT))
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @Transactional(readOnly = true)
  public PersonnelResponse findById(UUID id) {
    Personnel personnel = _personnelRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personnel not found"));
    return toResponse(Objects.requireNonNull(personnel));
  }

  @Transactional(rollbackFor = Throwable.class)
  public PersonnelResponse create(PersonnelRequest request) {
    Personnel personnel = new Personnel(
      request.fullName(),
      request.contactNumber(),
      request.email(),
      request.role()
    );
    return toResponse(_personnelRepository.save(personnel));
  }


  @Transactional(rollbackFor = Throwable.class)
  public PersonnelResponse update(UUID id, PersonnelRequest request) {
    Personnel personnel = _personnelRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personnel not found"));

    personnel.setFullName(request.fullName());
    personnel.setContactNumber(request.contactNumber());
    personnel.setEmail(request.email());
    personnel.setRole(request.role());

    return toResponse(_personnelRepository.save(personnel));
  }

  @Transactional(rollbackFor = Throwable.class)
  public void delete(UUID id) {
    Personnel personnel = _personnelRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personnel not found"));
    
      if (_vehicleRepository.existsByAssignedPersonnelIdAndDeletedAtIsNull(id)) {
      throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        "Personnel assigned to a vehicle cannot be deleted"
      );
    }

    if(personnel != null) {
      _personnelRepository.delete(personnel);
    }
  }

  private PersonnelResponse toResponse(Personnel personnel) {
    Personnel ensuredPersonnel = Objects.requireNonNull(personnel, "Personnel must not be null");
    return new PersonnelResponse(
      Objects.requireNonNull(ensuredPersonnel.getId(), "Personnel identifier must not be null"),
      ensuredPersonnel.getFullName(),
      ensuredPersonnel.getContactNumber(),
      ensuredPersonnel.getEmail(),
      ensuredPersonnel.getRole(),
      ensuredPersonnel.getCreatedAt(),
      ensuredPersonnel.getUpdatedAt()
    );
  }
}
