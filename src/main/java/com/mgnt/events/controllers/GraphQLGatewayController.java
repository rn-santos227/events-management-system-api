package com.mgnt.events.controllers;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.mgnt.events.responses.privileges.PrivilegeResponse;
import com.mgnt.events.responses.roles.RoleResponse;
import com.mgnt.events.responses.users.UserResponse;
import com.mgnt.events.services.PrivilegeService;
import com.mgnt.events.services.RoleService;
import com.mgnt.events.services.UserService;

@Controller
public class GraphQLGatewayController {
  private final UserService _userService;
  private final RoleService _roleService;
  private final PrivilegeService _privilegeService;

  public GraphQLGatewayController(
    UserService userService,
    RoleService roleService,
    PrivilegeService privilegeService
  ) {
    this._userService = userService;
    this._roleService = roleService;
    this._privilegeService = privilegeService;
  }

  @QueryMapping
  public String status() {
    return "OK";
  }

  @QueryMapping
  public List<UserResponse> users() {
    return _userService.findAll();
  }

  @QueryMapping
  public UserResponse user(@Argument long id) {
    return _userService.findById(id);
  }

  @QueryMapping
  public List<RoleResponse> roles() {
    return _roleService.findAll();
  }

  @QueryMapping
  public RoleResponse role(@Argument long id) {
    return _roleService.findById(id);
  }

  @QueryMapping
  public List<PrivilegeResponse> privileges() {
    return _privilegeService.findAll();
  }

  @QueryMapping
  public PrivilegeResponse privilege(@Argument long id) {
    return _privilegeService.findById(id);
  }
}
