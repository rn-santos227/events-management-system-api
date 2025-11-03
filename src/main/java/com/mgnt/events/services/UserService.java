package com.mgnt.events.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
  @NonNull
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

  @Transactional(readOnly = true)
  public List<UserResponse> findAll() {
    return userRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public UserResponse findById(Long id) {
    return toResponse(getUser(id));
  }

  private User getUser(Long id) {
    return userRepository
      .findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
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
