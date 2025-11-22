package com.mgnt.events.controllers;

import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.PrivilegeActions;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
import com.mgnt.events.responses.audit.AuditLogResponse;
import com.mgnt.events.security.annotations.RequiresPrivilege;
import com.mgnt.events.services.AuditLogService;
import com.mgnt.events.util.RequestValidators;

@RestController
@RequestMapping(Routes.AUDIT_LOGS)
public class AuditLogController {
  private final AuditLogService _auditLogService;

  public AuditLogController(AuditLogService auditLogService) {
    this._auditLogService = auditLogService;
  }

  @GetMapping
  @RequiresPrivilege({ PrivilegeActions.AUDIT_LOGS_READ })
  public List<AuditLogResponse> findAll(@RequestParam(name = Queries.LIMIT, required = false) Integer limit) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    return _auditLogService.findAll(sanitizedLimit);
  }

  @GetMapping(Routes.APPEND_USER_ID)
  @RequiresPrivilege({ PrivilegeActions.AUDIT_LOGS_READ, PrivilegeActions.AUDIT_LOGS_READ_OWN })
  public List<AuditLogResponse> findByUserId(
    @PathVariable @NonNull Long id,
    @RequestParam(name = Queries.LIMIT, required = false) Integer limit
  ) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    Authentication authentication = getAuthentication();
    if (hasAuthority(authentication, PrivilegeActions.AUDIT_LOGS_READ)) {
      return _auditLogService.findByUserId(id, sanitizedLimit);
    }
  }

  private Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  private boolean hasAuthority(Authentication authentication, String authority) {
    return authentication != null && authentication
      .getAuthorities()
      .stream()
      .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
  }

  private Long extractUserId(Authentication authentication) {
    if (authentication == null || authentication.getPrincipal() == null) {
      return null;
    }

    Object principal = authentication.getPrincipal();
    if (principal instanceof com.mgnt.events.models.User user) {
      return user.getId();
    }

    return null;
  }
}
