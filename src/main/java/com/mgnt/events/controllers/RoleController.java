package com.mgnt.events.controllers;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
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
}
