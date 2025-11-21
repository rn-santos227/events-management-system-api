package com.mgnt.events.services;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mgnt.events.constants.Queries;
import com.mgnt.events.models.AuditLog;
import com.mgnt.events.models.User;
import com.mgnt.events.repositories.AuditLogRepository;

import jakarta.servlet.http.HttpServletRequest;

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
  }

  private User resolveUser() {
    
  }
}
