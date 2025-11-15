package com.mgnt.events.cli;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mgnt.events.models.User;
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

  @Transactional(rollbackFor = Throwable.class)
  public void seed(SeedUserOptions options) {
    String normalizedEmail = options.email();
    Optional<User> _existingUser = _userRepository.findByEmail(normalizedEmail);
    
    if (_existingUser.isPresent() && !options.force()) {
      System.out.printf("User %s already exists. Use --force to update.%n", normalizedEmail);
      return;
    }
  }
}
