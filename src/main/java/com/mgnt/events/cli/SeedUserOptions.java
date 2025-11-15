package com.mgnt.events.cli;

record SeedUserOptions(
  String email,
  String password,
  String firstName,
  String lastName,
  String contactNumber,
  String role,
  boolean active,
  boolean force
) {

}
