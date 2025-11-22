package com.mgnt.events.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UrlPathHelper;

import com.mgnt.events.services.AuditLogService;

@Component
public class AuditTrailInterceptor extends HandlerInterceptor {
  private final AuditLogService _auditLogService;
  private final UrlPathHelper _urlPathHelper = new UrlPathHelper();

  @Override
  public void afterCompletion(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull Object handler,
    Exception ex
  ) throws Exception {

  }    
}
