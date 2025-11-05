package com.mgnt.events.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.services.RoleService;

@RestController
@RequestMapping(Routes.ROLES)
public class RoleController {
  private final RoleService _roleService;

  public RoleController(RoleService roleService) {
    this._roleService = roleService;
  }
}
