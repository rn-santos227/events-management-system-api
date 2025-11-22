package com.mgnt.events.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.events.constants.Routes;
import com.mgnt.events.services.AuditLogService;

@RestController
@RequestMapping(Routes.AUDIT_LOGS)
public class AuditLogController {
  private final AuditLogService _auditLogService;

  public AuditLogController(AuditLogService auditLogService) {
    this._auditLogService = auditLogService;
  }
}
