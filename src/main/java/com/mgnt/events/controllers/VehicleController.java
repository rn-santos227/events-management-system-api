package com.mgnt.events.controllers;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
import com.mgnt.events.requests.vehicles.VehicleRequest;
import com.mgnt.events.responses.vehicles.VehicleResponse;
import com.mgnt.events.services.VehicleService;
import com.mgnt.events.util.RequestValidators;

@RestController
@RequestMapping(Routes.VEHICLES)
public class VehicleController {
  private final VehicleService _vehicleService;

  public VehicleController(VehicleService vehicleService) {
    this._vehicleService = vehicleService;
  }

  @GetMapping
  public List<VehicleResponse> findAll(
    @RequestParam(name = Queries.LIMIT, required = false) Integer limit
  ) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    return _vehicleService.findAll(sanitizedLimit);
  }

  @GetMapping(Routes.APPEND_ID)
  public VehicleResponse findById(@PathVariable @NonNull UUID id) {
    return _vehicleService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public VehicleResponse create(@Valid @RequestBody VehicleRequest request) {
    return _vehicleService.create(request);
  }

  @PutMapping(Routes.APPEND_ID)
  public VehicleResponse update(@PathVariable @NonNull UUID id, @Valid @RequestBody VehicleRequest request) {
    return _vehicleService.update(id, request);
  }

  @DeleteMapping(Routes.APPEND_ID)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NonNull UUID id) {
    _vehicleService.delete(id);
  }
}
