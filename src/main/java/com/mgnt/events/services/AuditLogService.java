package com.mgnt.events.services;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.models.AuditLog;
import com.mgnt.events.models.User;
import com.mgnt.events.repositories.AuditLogRepository;
import com.mgnt.events.responses.audit.AuditLogResponse;

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

  private AuditLogResponse toResponse(AuditLog auditLog) {
    Objects.requireNonNull(auditLog, "Audit log must not be null");
    User user = auditLog.getUser();
  }

  private User requireUser() {
    User user = resolveUser();
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
    }
    return user;
  }
}
