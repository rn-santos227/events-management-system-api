package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.models.Role;
import com.mgnt.events.models.User;
import com.mgnt.events.repositories.RoleRepository;
import com.mgnt.events.repositories.UserRepository;
import com.mgnt.events.responses.roles.RoleSummary;
import com.mgnt.events.responses.users.UserResponse;

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

  private Role getRole(Long id) {
    return roleRepository
      .findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
  }

  private UserResponse toResponse(User user) {
    Role role = user.getRole();
    RoleSummary roleSummary = role != null ? new RoleSummary(role.getId(), role.getName()) : null;

    return new UserResponse(
      user.getId(),
      user.getEmail(),
      user.getFirstName(),
      user.getLastName(),
      user.getContactNumber(),
      user.isActive(),
      roleSummary,
      user.getCreatedAt(),
      user.getUpdatedAt(),
      user.getDeletedAt()
    );
  }
}
