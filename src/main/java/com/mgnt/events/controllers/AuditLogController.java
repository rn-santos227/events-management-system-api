package com.mgnt.events.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.PrivilegeActions;
import com.mgnt.events.constants.Queries;
import com.mgnt.events.constants.Routes;
import com.mgnt.events.responses.audit.AuditLogResponse;
import com.mgnt.events.security.annotations.RequiresPrivilege;
import com.mgnt.events.services.AuditLogService;

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

  }
}
