package com.mgnt.events.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mgnt.events.security.PrivilegeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  private final PrivilegeInterceptor privilegeInterceptor;

  public WebConfig(PrivilegeInterceptor privilegeInterceptor) {
    this.privilegeInterceptor = privilegeInterceptor;
  }
}
