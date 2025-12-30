package com.mgnt.events.services;

import static com.mgnt.events.constants.Cache.AUDIT_LOGS;
import static com.mgnt.events.constants.Cache.AUDIT_LOGS_BY_USER;
import static com.mgnt.events.constants.Cache.KEY;
import static com.mgnt.events.constants.Cache.KEY_ALL;
import static com.mgnt.events.constants.Cache.KEY_USER;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.models.AuditLog;
import com.mgnt.events.models.User;
import com.mgnt.events.repositories.AuditLogRepository;
import com.mgnt.events.responses.audit.AuditLogResponse;
import com.mgnt.events.responses.audit.AuditLogUserResponse;
import com.mgnt.events.util.RequestValidators;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuditLogService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, Queries.CREATED_AT);
  private final AuditLogRepository _auditLogRepository;

  public AuditLogService(AuditLogRepository auditLogRepository) {
    this._auditLogRepository = auditLogRepository;
  }

  @CacheEvict(cacheNames = { AUDIT_LOGS, AUDIT_LOGS_BY_USER }, allEntries = true)
  public void record(String action, HttpServletRequest request, int statusCode, String message) {
    AuditLog auditLog = new AuditLog();
    auditLog.setAction(action);
    auditLog.setMethod(request.getMethod());
    auditLog.setPath(request.getRequestURI());
    auditLog.setStatusCode(statusCode);
    auditLog.setIpAddress(request.getRemoteAddr());
    auditLog.setMessage(message);
    auditLog.setUser(resolveUser());

    _auditLogRepository.save(auditLog);
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = AUDIT_LOGS, key = KEY_ALL)
  public List<AuditLogResponse> findAll(@Nullable Integer limit) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    if (sanitizedLimit == null) {
      return _auditLogRepository.findAll(DEFAULT_SORT).stream().map(this::toResponse).toList();
    }

    return _auditLogRepository
      .findAll(PageRequest.of(0, sanitizedLimit, DEFAULT_SORT))
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = AUDIT_LOGS_BY_USER, key = KEY)
  public List<AuditLogResponse> findByUserId(@NonNull UUID userId, @Nullable Integer limit) {
    return applyUserLimit(userId, limit).stream().map(this::toResponse).toList();
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = AUDIT_LOGS_BY_USER, key = KEY_USER)
  public List<AuditLogResponse> findForAuthenticatedUser(@Nullable Integer limit) {
    User user = requireUser();
    return applyUserLimit(Objects.requireNonNull(user.getId(), "User identifier must not be null"), limit)
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @Transactional(readOnly = true)
  public List<AuditLogResponse> search(
    @Nullable String action,
    @Nullable List<String> actions,
    @Nullable List<String> activities,
    @Nullable String method,
    @Nullable String path,
    @Nullable Integer statusCode,
    @Nullable String ipAddress,
    @Nullable String message,
    @Nullable UUID userId,
    @Nullable LocalDateTime startDate,
    @Nullable LocalDateTime endDate,
    @Nullable Integer limit
  ) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "end date must be on or after start date");
    }

    List<String> actionTokens = new ArrayList<>();
    if (!RequestValidators.isBlank(action) && action != null) {
      actionTokens.add(action.toString().trim());
    }
    actionTokens.addAll(sanitizeTokens(actions));
    actionTokens.addAll(sanitizeTokens(activities));

    Specification<AuditLog> specification = (root, query, builder) -> builder.conjunction();
    if (!actionTokens.isEmpty()) {
      specification = specification.and((root, query, builder) -> root.get(Queries.ACTION).in(actionTokens));
    }

    if (!RequestValidators.isBlank(method) && method != null) {
      String sanitizedMethod = method.trim().toLowerCase();
      specification =
        specification.and(
          (root, query, builder) ->
            builder.like(builder.lower(root.get(Queries.METHOD)), "%" + sanitizedMethod + "%")
        );
    }

    if (!RequestValidators.isBlank(path) && path != null) {
      String sanitizedPath = path.trim().toLowerCase();
      specification =
        specification.and(
          (root, query, builder) ->
            builder.like(builder.lower(root.get(Queries.PATH)), "%" + sanitizedPath + "%")
        );
    }

    if (statusCode != null) {
      specification =
        specification.and((root, query, builder) -> builder.equal(root.get(Queries.STATUS_CODE), statusCode));
    }

  }

  private AuditLogResponse toResponse(AuditLog auditLog) {
    Objects.requireNonNull(auditLog, "Audit log must not be null");
    User user = auditLog.getUser();
    AuditLogUserResponse userResponse =
      user == null
        ? null
        : new AuditLogUserResponse(
          Objects.requireNonNull(user.getId(), "User identifier must not be null"),
          user.getEmail(),
          user.getFullName()
        );

    return new AuditLogResponse(
      Objects.requireNonNull(auditLog.getId(), "Audit log identifier must not be null"),
      auditLog.getAction(),
      auditLog.getMethod(),
      auditLog.getPath(),
      auditLog.getStatusCode(),
      auditLog.getIpAddress(),
      auditLog.getMessage(),
      userResponse,
      auditLog.getCreatedAt()
    );
  }

  private List<AuditLog> applyUserLimit(UUID userId, @Nullable Integer limit) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    if (sanitizedLimit == null) {
      return _auditLogRepository.findAllByUserId(userId, DEFAULT_SORT);
    }

    return _auditLogRepository
      .findAllByUserId(userId, PageRequest.of(0, sanitizedLimit, DEFAULT_SORT))
      .getContent();
  }

  private User resolveUser() {
   Authentication _authentication = SecurityContextHolder.getContext().getAuthentication();
    if (_authentication == null) {
      return null;
    }

    Object principal = _authentication.getPrincipal();
    if (principal instanceof User user) {
      return user;
    }

    return null;
  }

  private User requireUser() {
    User user = resolveUser();
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
    }
    return user;
  }

  private List<String> sanitizeTokens(@Nullable List<String> tokens) {
    if (tokens == null) {
      return List.of();
    }
    return tokens.stream().filter(token -> !RequestValidators.isBlank(token)).map(token -> token.trim()).toList();
  }
}
