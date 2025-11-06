package com.mgnt.events.services;

import java.util.List;
import java.util.Objects;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Attributes;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.models.Role;
import com.mgnt.events.models.User;
import com.mgnt.events.repositories.RoleRepository;
import com.mgnt.events.repositories.UserRepository;
import com.mgnt.events.requests.users.UserCreateRequest;
import com.mgnt.events.requests.users.UserUpdateRequest;
import com.mgnt.events.responses.roles.RoleSummary;
import com.mgnt.events.responses.users.UserResponse;
import com.mgnt.events.utils.RequestValidators;

@Service
public class UserService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, Attributes.EMAIL);
  private final UserRepository _userRepository;
  private final RoleRepository _roleRepository;
  private final PasswordEncoder _passwordEncoder;

  public UserService(
    UserRepository userRepository,
    RoleRepository roleRepository,
    PasswordEncoder passwordEncoder
  ) {
    this._userRepository = userRepository;
    this._roleRepository = roleRepository;
    this._passwordEncoder = passwordEncoder;
  }

  @Transactional(readOnly = true)
  public List<UserResponse> findAll(@Nullable Integer limit) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    if (sanitizedLimit == null) {
      return _userRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
    }

    return _userRepository
      .findAll(PageRequest.of(0, sanitizedLimit, DEFAULT_SORT))
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @Transactional(readOnly = true)
  public UserResponse findById(@NonNull Long id) {
    return toResponse(getUser(id));
  }

  @Transactional
  public UserResponse create(UserCreateRequest request) {
    String normalizedEmail = normalizeEmail(request.email());
    if (_userRepository.existsByEmail(normalizedEmail)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
    }

    Long roleId = RequestValidators.requireNonNull(request.roleId(), "Role ID");
    Role role = getRole(roleId);
    User user = new User(
      normalizedEmail,
      _passwordEncoder.encode(request.password()),
      request.firstName(),
      request.lastName(),
      request.contactNumber(),
      role
    );

    if (request.active() != null) {
      user.setActive(request.active());
    }

    return toResponse(_userRepository.save(user));
  }

  @Transactional
  public UserResponse update(@NonNull Long id, UserUpdateRequest request) {
    User user = getUser(id);

    if (_userRepository.existsByEmailAndIdNot(request.email(), id)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
    }

    user.setEmail(request.email());
    user.setFirstName(request.firstName());
    user.setLastName(request.lastName());
    user.setContactNumber(request.contactNumber());

    Long roleId = RequestValidators.requireNonNull(request.roleId(), "Role ID");
    user.setRole(getRole(roleId));

    if (!RequestValidators.isBlank(request.password())) {
      user.setPassword(_passwordEncoder.encode(request.password()));
    }

    if (request.active() != null) {
      user.setActive(request.active());
    }

    return toResponse(_userRepository.save(user));
  }

  @Transactional
  public void delete(@NonNull Long id) {
    User user = Objects.requireNonNull(getUser(id));
    try {
      _userRepository.delete(user);
    } catch (DataIntegrityViolationException exception) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Unable to delete user", exception);
    }
  }

  private User getUser(@NonNull Long id) {
    return Objects.requireNonNull(
      _userRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))
    );
  }

  private Role getRole(@NonNull Long id) {
    return Objects.requireNonNull(
      _roleRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"))
    );
  }

  private String normalizeEmail(String email) {
    return email == null ? null : email.trim().toLowerCase();
  }

  private UserResponse toResponse(User user) {
    User ensuredUser = Objects.requireNonNull(user, "User must not be null");
    Role role = ensuredUser.getRole();
    RoleSummary roleSummary =
      role != null
        ? new RoleSummary(
          Objects.requireNonNull(role.getId(), "Role identifier must not be null"),
          role.getName()
        )
        : null;

    return new UserResponse(
      Objects.requireNonNull(ensuredUser.getId(), "User identifier must not be null"),
      ensuredUser.getEmail(),
      ensuredUser.getFirstName(),
      ensuredUser.getLastName(),
      ensuredUser.getContactNumber(),
      ensuredUser.isActive(),
      roleSummary,
      ensuredUser.getCreatedAt(),
      ensuredUser.getUpdatedAt(),
      ensuredUser.getDeletedAt()
    );
  }
}
