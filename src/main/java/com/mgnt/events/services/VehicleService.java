package com.mgnt.events.services;

import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.Personnel;
import com.mgnt.events.models.Vehicle;
import com.mgnt.events.repositories.PersonnelRepository;
import com.mgnt.events.repositories.VehicleRepository;
import com.mgnt.events.responses.vehicles.VehiclePersonnelSummary;
import com.mgnt.events.responses.vehicles.VehicleResponse;

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
  public List<VehicleResponse> findAll(@Nullable Integer limit) {

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
}
