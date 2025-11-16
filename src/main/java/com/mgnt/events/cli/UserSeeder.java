package com.mgnt.events.cli;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mgnt.events.models.User;
import com.mgnt.events.constants.Seeds;
import com.mgnt.events.models.Privilege;
import com.mgnt.events.models.Role;
import com.mgnt.events.repositories.PrivilegeRepository;
import com.mgnt.events.repositories.RoleRepository;
import com.mgnt.events.repositories.UserRepository;

@Component
public class UserSeeder {
  private final UserRepository _userRepository;
  private final RoleRepository _roleRepository;
  private final PrivilegeRepository _privilegeRepository;
  private final PasswordEncoder _passwordEncoder;

  public UserSeeder(
    UserRepository userRepository,
    RoleRepository roleRepository,
    PrivilegeRepository privilegeRepository,
    PasswordEncoder passwordEncoder
  ) {
    this._userRepository = userRepository;
    this._roleRepository = roleRepository;
    this._privilegeRepository = privilegeRepository;
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

    Role _role = _roleRepository
      .findByNameIgnoreCase(options.role())
      .orElseThrow(() -> new IllegalArgumentException("Role %s not found".formatted(options.role())));

    User user = _existingUser.orElseGet(User::new);
    user.setEmail(normalizedEmail);
    user.setFirstName(options.firstName());
    user.setLastName(options.lastName());
    user.setContactNumber(options.contactNumber());
    user.setRole(_role);
    user.setActive(options.active());
    user.setPassword(_passwordEncoder.encode(options.password()));

    _userRepository.save(user);
    if (_existingUser.isPresent()) {
      System.out.printf("Updated user %s with role %s.%n", normalizedEmail, _role.getName());
    } else {
      System.out.printf("Created user %s with role %s.%n", normalizedEmail, _role.getName());
    }
  }

  private Role initializeRole(String roleName) {
    Role _role = new Role(roleName.toUpperCase());

    if (Seeds.ROLE_DEFAULT.equalsIgnoreCase(roleName)) {
      List<Privilege> privileges = _privilegeRepository.findAll();
      _role.setPrivileges(new LinkedHashSet<>(privileges));
    }
  }
}
