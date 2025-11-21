package com.mgnt.events.services;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

@Service
public class AuditLogService {
  @NonNull
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, Queries.CREATED_AT);
  private final AuditLogRepository _auditLogRepository;

  public AuditLogService(AuditLogRepository auditLogRepository) {
    this._auditLogRepository = auditLogRepository;
  }

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
  public List<AuditLogResponse> findByUserId(@NonNull Long userId, @Nullable Integer limit) {
    return applyUserLimit(userId, limit).stream().map(this::toResponse).toList();
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

  private List<AuditLog> applyUserLimit(Long userId, @Nullable Integer limit) {
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
}
