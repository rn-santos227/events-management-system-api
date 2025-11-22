package com.mgnt.events.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.responses.privileges.PrivilegeResponse;
import com.mgnt.events.responses.roles.RoleResponse;
import com.mgnt.events.responses.users.UserResponse;
import com.mgnt.events.services.PrivilegeService;
import com.mgnt.events.services.RoleService;
import com.mgnt.events.services.UserService;
import com.mgnt.events.util.RequestValidators;

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
  public List<UserResponse> users(@Argument Integer limit) {
    Integer sanitizedLimit = sanitizeLimit(limit);
    return _userService.findAll(sanitizedLimit);
  }

  @QueryMapping
  public UserResponse user(@Argument @NonNull UUID id) {
    return _userService.findById(id);
  }

  @QueryMapping
  public List<RoleResponse> roles(@Argument Integer limit) {
    Integer sanitizedLimit = sanitizeLimit(limit);
    return _roleService.findAll(sanitizedLimit);
  }

  @QueryMapping
  public RoleResponse role(@Argument UUID id) {
    return _roleService.findById(id);
  }

  @QueryMapping
  public List<PrivilegeResponse> privileges(@Argument Integer limit) {
    Integer sanitizedLimit = sanitizeLimit(limit);
    return _privilegeService.findAll(sanitizedLimit);
  }

  @QueryMapping
  public PrivilegeResponse privilege(@Argument @NonNull UUID id) {
    return _privilegeService.findById(id);
  }

  private Integer sanitizeLimit(Integer limit) {
    return RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
  }
}
