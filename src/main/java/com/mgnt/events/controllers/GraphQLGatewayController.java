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

  @QueryMapping
  public String status() {
    return "OK";
  }

  @QueryMapping
  public List<UserResponse> users() {
    return userService.findAll();
  }

  @QueryMapping
  public UserResponse user(@Argument long id) {
    return userService.findById(id);
  }

  @QueryMapping
  public List<RoleResponse> roles() {
    return roleService.findAll();
  }

  @QueryMapping
  public RoleResponse role(@Argument long id) {
    return roleService.findById(id);
  }

  @QueryMapping
  public List<PrivilegeResponse> privileges() {
    return privilegeService.findAll();
  }

  @QueryMapping
  public PrivilegeResponse privilege(@Argument long id) {
    return privilegeService.findById(id);
  }
}
