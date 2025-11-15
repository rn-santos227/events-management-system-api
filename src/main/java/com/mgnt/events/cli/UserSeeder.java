package com.mgnt.events.cli;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mgnt.events.repositories.RoleRepository;
import com.mgnt.events.repositories.UserRepository;

@Component
public class UserSeeder {
  private final UserRepository _userRepository;
  private final RoleRepository _roleRepository;
  private final PasswordEncoder _passwordEncoder;

  public UserSeeder(
    UserRepository userRepository,
    RoleRepository roleRepository,
    PasswordEncoder passwordEncoder
  ) {
    this._userRepository = userRepository;
    this._roleRepository = roleRepository;
    this._passwordEncoder = passwordEncoder;
  }
}
