package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.repositories.RoleRepository;
import com.mgnt.events.repositories.UserRepository;

@Service
public class UserService {
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.EMAIL);

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(
    UserRepository userRepository,
    RoleRepository roleRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }
}
