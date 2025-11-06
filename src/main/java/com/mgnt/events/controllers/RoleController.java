package com.mgnt.events.controllers;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.requests.roles.RoleRequest;
import com.mgnt.events.responses.roles.RoleResponse;
import com.mgnt.events.services.RoleService;

@RestController
@RequestMapping(Routes.ROLES)
public class RoleController {
  private final RoleService _roleService;

  public RoleController(RoleService roleService) {
    this._roleService = roleService;
  }

  @GetMapping
  public List<RoleResponse> findAll() {
    return _roleService.findAll();
  }

  @GetMapping(Routes.APPEND_ID)
  public RoleResponse findById(@PathVariable @NonNull Long id) {
    return _roleService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RoleResponse create(@Valid @RequestBody RoleRequest request) {
    return _roleService.create(request);
  }

  @PutMapping(Routes.APPEND_ID)
  public RoleResponse update(
    @PathVariable @NonNull Long id,
    @Valid @RequestBody RoleRequest request
  ) {
    return _roleService.update(id, request);
  }

  @DeleteMapping(Routes.APPEND_ID)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NonNull Long id) {
    _roleService.delete(id);
  }
}
