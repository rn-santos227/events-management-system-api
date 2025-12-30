package com.mgnt.events.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.format.annotation.DateTimeFormat;

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

  @GetMapping(Routes.SEARCH)
  @RequiresPrivilege({ PrivilegeActions.AUDIT_LOGS_READ })
  public List<AuditLogResponse> search(
    @RequestParam(name = Queries.ACTION, required = false) String action,
    @RequestParam(name = Queries.ACTIONS, required = false) List<String> actions,
    @RequestParam(name = Queries.ACTIVITIES, required = false) List<String> activities,
    @RequestParam(name = Queries.METHOD, required = false) String method,
    @RequestParam(name = Queries.PATH, required = false) String path,
    @RequestParam(name = Queries.STATUS_CODE, required = false) Integer statusCode,
    @RequestParam(name = Queries.IP_ADDRESS, required = false) String ipAddress,
    @RequestParam(name = Queries.MESSAGE, required = false) String message,
    @RequestParam(name = Queries.USER_ID, required = false) UUID userId,
    @RequestParam(name = Queries.START_DATE, required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime startDate,
    @RequestParam(name = Queries.END_DATE, required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime endDate,
    @RequestParam(name = Queries.LIMIT, required = false) Integer limit
  ) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
  }

  @GetMapping(Routes.APPEND_USER_ID)
  @RequiresPrivilege({ PrivilegeActions.AUDIT_LOGS_READ, PrivilegeActions.AUDIT_LOGS_READ_OWN })
  public List<AuditLogResponse> findByUserId(
    @PathVariable @NonNull UUID id,
    @RequestParam(name = Queries.LIMIT, required = false) Integer limit
  ) {
    Integer sanitizedLimit = RequestValidators.requirePositiveOrNull(limit, Queries.LIMIT);
    Authentication authentication = getAuthentication();
    if (hasAuthority(authentication, PrivilegeActions.AUDIT_LOGS_READ)) {
      return _auditLogService.findByUserId(id, sanitizedLimit);
    }

    if (authentication == null || authentication.getPrincipal() == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
    }

    UUID authenticatedUserId = extractUserId(authentication);
    if (authenticatedUserId == null || !authenticatedUserId.equals(id)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot view other users' audit logs");
    }

    return _auditLogService.findForAuthenticatedUser(sanitizedLimit);
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

  private UUID extractUserId(Authentication authentication) {
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
