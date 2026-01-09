package com.mgnt.events.controllers;

import java.util.UUID;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;

import com.mgnt.events.requests.accommodations.AccommodationUpdateRequest;
import com.mgnt.events.requests.categories.CategoryUpdateRequest;
import com.mgnt.events.requests.personnel.PersonnelUpdateRequest;
import com.mgnt.events.requests.privileges.PrivilegeUpdateRequest;
import com.mgnt.events.requests.roles.RoleUpdateRequest;
import com.mgnt.events.requests.settings.UserSettingUpdateRequest;
import com.mgnt.events.requests.users.UserPatchRequest;
import com.mgnt.events.requests.vehicles.VehicleUpdateRequest;
import com.mgnt.events.requests.venues.VenueUpdateRequest;
import com.mgnt.events.responses.accommodations.AccommodationResponse;
import com.mgnt.events.responses.categories.CategoryResponse;
import com.mgnt.events.responses.personnel.PersonnelResponse;
import com.mgnt.events.responses.privileges.PrivilegeResponse;
import com.mgnt.events.responses.roles.RoleResponse;
import com.mgnt.events.responses.settings.UserSettingResponse;
import com.mgnt.events.responses.users.UserResponse;
import com.mgnt.events.responses.vehicles.VehicleResponse;
import com.mgnt.events.responses.venues.VenueResponse;
import com.mgnt.events.services.AccommodationService;
import com.mgnt.events.services.CategoryService;
import com.mgnt.events.services.PersonnelService;
import com.mgnt.events.services.PrivilegeService;
import com.mgnt.events.services.RoleService;
import com.mgnt.events.services.UserSettingService;
import com.mgnt.events.services.UserService;
import com.mgnt.events.services.VehicleService;
import com.mgnt.events.services.VenueService;

@Controller
public class GraphQLMutationController {
  private final AccommodationService _accommodationService;
  private final CategoryService _categoryService;
  private final PersonnelService _personnelService;
  private final PrivilegeService _privilegeService;
  private final RoleService _roleService;
  private final UserSettingService _userSettingService;
  private final UserService _userService;
  private final VehicleService _vehicleService;
  private final VenueService _venueService;

  public GraphQLMutationController(
    AccommodationService accommodationService,
    CategoryService categoryService,
    PersonnelService personnelService,
    PrivilegeService privilegeService,
    RoleService roleService,
    UserSettingService userSettingService,
    UserService userService,
    VehicleService vehicleService,
    VenueService venueService
  ) {
    this._accommodationService = accommodationService;
    this._categoryService = categoryService;
    this._personnelService = personnelService;
    this._privilegeService = privilegeService;
    this._roleService = roleService;
    this._userSettingService = userSettingService;
    this._userService = userService;
    this._vehicleService = vehicleService;
    this._venueService = venueService;
  }

  @MutationMapping
  public AccommodationResponse updateAccommodation(
    @Argument @NonNull UUID id,
    @Argument AccommodationUpdateRequest input
  ) {
    return _accommodationService.updatePartial(id, input);
  }

  @MutationMapping
  public CategoryResponse updateCategory(
    @Argument @NonNull UUID id,
    @Argument CategoryUpdateRequest input
  ) {
    return _categoryService.updatePartial(id, input);
  }

  @MutationMapping
  public PersonnelResponse updatePersonnel(
    @Argument @NonNull UUID id,
    @Argument PersonnelUpdateRequest input
  ) {
    return _personnelService.updatePartial(id, input);
  }

  @MutationMapping
  public PrivilegeResponse updatePrivilege(
    @Argument @NonNull UUID id,
    @Argument PrivilegeUpdateRequest input
  ) {
    return _privilegeService.updatePartial(id, input);
  }

  @MutationMapping
  public RoleResponse updateRole(
    @Argument @NonNull UUID id,
    @Argument RoleUpdateRequest input
  ) {
    return _roleService.updatePartial(id, input);
  }

  @MutationMapping
  public UserResponse updateUser(
    @Argument @NonNull UUID id,
    @Argument UserPatchRequest input
  ) {
    return _userService.updatePartial(id, input);
  }

  @MutationMapping
  public UserSettingResponse updateUserSetting(
    @Argument @NonNull UUID userId,
    @Argument UserSettingUpdateRequest input
  ) {
    return _userSettingService.updatePartial(userId, input);
  }

  @MutationMapping
  public VehicleResponse updateVehicle(
    @Argument @NonNull UUID id,
    @Argument VehicleUpdateRequest input
  ) {
    return _vehicleService.updatePartial(id, input);
  }

  @MutationMapping
  public VenueResponse updateVenue(
    @Argument @NonNull UUID id,
    @Argument VenueUpdateRequest input
  ) {
    return _venueService.updatePartial(id, input);
  }
}
