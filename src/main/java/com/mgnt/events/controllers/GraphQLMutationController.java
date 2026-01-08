package com.mgnt.events.controllers;

import org.springframework.stereotype.Controller;

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
}
