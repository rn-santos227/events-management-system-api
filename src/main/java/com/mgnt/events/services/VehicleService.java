package com.mgnt.events.services;

import static com.mgnt.events.constants.Cache.VEHICLE_BY_ID;
import static com.mgnt.events.constants.Cache.VEHICLES;
import static com.mgnt.events.constants.Cache.KEY;

import java.util.List;
import java.util.Objects;
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
import com.mgnt.events.models.Personnel;
import com.mgnt.events.models.Vehicle;
import com.mgnt.events.repositories.PersonnelRepository;
import com.mgnt.events.repositories.VehicleRepository;
import com.mgnt.events.requests.vehicles.VehicleRequest;
import com.mgnt.events.responses.vehicles.VehiclePersonnelSummary;
import com.mgnt.events.responses.vehicles.VehicleResponse;
import com.mgnt.events.util.RequestValidators;

@Service
public class VehicleService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.NAME);

  private final VehicleRepository _vehicleRepository;
  private final PersonnelRepository _personnelRepository;

  public VehicleService(VehicleRepository vehicleRepository, PersonnelRepository personnelRepository) {
    this._vehicleRepository = vehicleRepository;
    this._personnelRepository = personnelRepository;
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = VEHICLES, key = KEY)
  public List<VehicleResponse> findAll(@Nullable Integer limit, @Nullable Integer page) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    Integer sanitizedPage =
      sanitizedLimit == null ? null : RequestValidators.requireNonNegativeOrNull(page, Queries.PAGE);
    if (sanitizedLimit == null) {
      return _vehicleRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
    }

    int resolvedPage = sanitizedPage != null ? sanitizedPage.intValue() : 0;

    return _vehicleRepository
      .findAll(PageRequest.of(resolvedPage, sanitizedLimit, DEFAULT_SORT))
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = VEHICLE_BY_ID, key = "#id")
  public VehicleResponse findById(UUID id) {
    Vehicle vehicle = _vehicleRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
    return toResponse(Objects.requireNonNull(vehicle));
  }

  @Transactional(rollbackFor = Throwable.class)
  @CacheEvict(cacheNames = { VEHICLES, VEHICLE_BY_ID }, allEntries = true)
  public VehicleResponse create(VehicleRequest request) {
    Vehicle vehicle = new Vehicle(
      request.name(),
      request.type(),
      request.status(),
      request.plateNumber(),
      request.contactNumber()
    );

    vehicle.setAssignedPersonnel(resolvePersonnel(request.assignedPersonnelId()));
    return toResponse(_vehicleRepository.save(vehicle));
  }

  @Transactional(rollbackFor = Throwable.class)
  @CacheEvict(cacheNames = { VEHICLES, VEHICLE_BY_ID }, allEntries = true)
  public VehicleResponse update(UUID id, VehicleRequest request) {
    Vehicle vehicle = _vehicleRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

    vehicle.setName(request.name());
    vehicle.setType(request.type());
    vehicle.setStatus(request.status());
    vehicle.setPlateNumber(request.plateNumber());
    vehicle.setContactNumber(request.contactNumber());
    vehicle.setAssignedPersonnel(resolvePersonnel(request.assignedPersonnelId()));

    return toResponse(_vehicleRepository.save(vehicle));
  }

  private VehicleResponse toResponse(Vehicle vehicle) {
    Vehicle ensuredVehicle = Objects.requireNonNull(vehicle, "Vehicle must not be null");
    return new VehicleResponse(
      Objects.requireNonNull(ensuredVehicle.getId(), "Vehicle identifier must not be null"),
      ensuredVehicle.getName(),
      ensuredVehicle.getType(),
      ensuredVehicle.getStatus(),
      ensuredVehicle.getPlateNumber(),
      ensuredVehicle.getContactNumber(),
      toPersonnelSummary(ensuredVehicle.getAssignedPersonnel()),
      ensuredVehicle.getCreatedAt(),
      ensuredVehicle.getUpdatedAt()
    );
  }

  @Transactional(rollbackFor = Throwable.class)
  @CacheEvict(cacheNames = { VEHICLES, VEHICLE_BY_ID }, allEntries = true)
  public void delete(UUID id) {
    Vehicle vehicle = _vehicleRepository
      .findById(
        RequestValidators.requireNonNull(id, "ID must not be null")
      )
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

    if(vehicle != null) {
      _vehicleRepository.delete(vehicle);
    }
  }

  private VehiclePersonnelSummary toPersonnelSummary(Personnel personnel) {
    if (personnel == null) {
      return null;
    }
    Personnel ensuredPersonnel = Objects.requireNonNull(personnel, "Personnel must not be null");
    return new VehiclePersonnelSummary(
      Objects.requireNonNull(ensuredPersonnel.getId(), "Personnel identifier must not be null"),
      ensuredPersonnel.getFullName(),
      ensuredPersonnel.getContactNumber(),
      ensuredPersonnel.getRole()
    );
  }

  private Personnel resolvePersonnel(UUID personnelId) {
    if (personnelId == null) {
      return null;
    }

    return _personnelRepository
      .findById(personnelId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assigned personnel not found"));
  }
}
