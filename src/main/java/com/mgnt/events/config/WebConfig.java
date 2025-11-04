package com.mgnt.events.config;

import org.springframework.context.annotation.Configuration;

import com.mgnt.events.security.PrivilegeInterceptor;

@Configuration
public class WebConfig {
  private final PrivilegeInterceptor privilegeInterceptor;

  public WebConfig(PrivilegeInterceptor privilegeInterceptor) {
    this.privilegeInterceptor = privilegeInterceptor;
  }
}
