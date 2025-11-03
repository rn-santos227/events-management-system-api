package com.mgnt.events.controllers;

import com.mgnt.events.services.PrivilegeService;
import com.mgnt.events.services.RoleService;
import com.mgnt.events.services.UserService;

public class GraphQLGatewayController {
  private final UserService userService;
  private final RoleService roleService;
  private final PrivilegeService privilegeService;

  public GraphQLGatewayController(
    UserService userService,
    RoleService roleService,
    PrivilegeService privilegeService
  ) {
    this.userService = userService;
    this.roleService = roleService;
    this.privilegeService = privilegeService;
  }
}
