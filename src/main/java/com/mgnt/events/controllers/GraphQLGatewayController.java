package com.mgnt.events.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.responses.accommodations.AccommodationResponse;
import com.mgnt.events.responses.audit.AuditLogResponse;
import com.mgnt.events.responses.categories.CategoryResponse;
import com.mgnt.events.responses.files.FileUploadResponse;
import com.mgnt.events.responses.personnel.PersonnelResponse;
import com.mgnt.events.responses.privileges.PrivilegeResponse;
import com.mgnt.events.responses.roles.RoleResponse;
import com.mgnt.events.responses.users.UserResponse;
import com.mgnt.events.responses.vehicles.VehicleResponse;
import com.mgnt.events.responses.venues.VenueResponse;
import com.mgnt.events.services.AccommodationService;
import com.mgnt.events.services.AuditLogService;
import com.mgnt.events.services.CategoryService;
import com.mgnt.events.services.FileStorageService;
import com.mgnt.events.services.PersonnelService;
import com.mgnt.events.services.PrivilegeService;
import com.mgnt.events.services.RoleService;
import com.mgnt.events.services.VehicleService;
import com.mgnt.events.services.VenueService;
import com.mgnt.events.services.UserService;
import com.mgnt.events.util.RequestValidators;

@Controller
public class GraphQLGatewayController {
  private final AuditLogService _auditLogService;
  private final AccommodationService _accommodationService;
  private final CategoryService _categoryService;
  private final FileStorageService _fileStorageService;
  private final PersonnelService _personnelService;
  private final PrivilegeService _privilegeService;
  private final RoleService _roleService;
  private final VehicleService _vehicleService;
  private final VenueService _venueService;
  private final UserService _userService;

  public GraphQLGatewayController(
    AuditLogService auditLogService,
    AccommodationService accommodationService,
    CategoryService categoryService,
    FileStorageService fileStorageService,
    PersonnelService personnelService,
    PrivilegeService privilegeService,
    RoleService roleService,
    VehicleService vehicleService,
    VenueService venueService,
    UserService userService
  ) {
    this._auditLogService = auditLogService;
    this._accommodationService = accommodationService;
    this._categoryService = categoryService;
    this._fileStorageService = fileStorageService;
    this._personnelService = personnelService;
    this._privilegeService = privilegeService;
    this._roleService = roleService;
    this._vehicleService = vehicleService;
    this._venueService = venueService;
    this._userService = userService;
  }

  @QueryMapping
  public String status() {
    return "OK";
  }

  @QueryMapping
  public List<AuditLogResponse> auditLogs(@Argument Integer limit) {
    Integer sanitizedLimit = sanitizeLimit(limit);
    return _auditLogService.findAll(sanitizedLimit);
  }

  @QueryMapping
  public List<AuditLogResponse> auditLogsByUser(@Argument @NonNull UUID userId, @Argument Integer limit) {
    Integer sanitizedLimit = sanitizeLimit(limit);
    return _auditLogService.findByUserId(userId, sanitizedLimit);
  }

  @QueryMapping
  public List<FileUploadResponse> files(@Argument Integer limit) {
    Integer sanitizedLimit = sanitizeLimit(limit);
    return _fileStorageService.findAll(sanitizedLimit);
  }

  @QueryMapping
  public FileUploadResponse file(@Argument @NonNull UUID id) {
    return _fileStorageService.findById(id);
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
  public List<UserResponse> users(@Argument Integer limit) {
    Integer sanitizedLimit = sanitizeLimit(limit);
    return _userService.findAll(sanitizedLimit);
  }

  @QueryMapping
  public UserResponse user(@Argument @NonNull UUID id) {
    return _userService.findById(id);
  }

  private Integer sanitizeLimit(Integer limit) {
    return RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
  }

  private Integer sanitizePage(Integer page) {
    return RequestValidators.requireNonNegativeOrNull(page, Queries.PAGE);
  }
}
