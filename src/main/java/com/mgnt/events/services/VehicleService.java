package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.Vehicle;
import com.mgnt.events.repositories.PersonnelRepository;
import com.mgnt.events.repositories.VehicleRepository;
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

  private VehicleResponse toResponse(Vehicle vehicle) {

  }
}
