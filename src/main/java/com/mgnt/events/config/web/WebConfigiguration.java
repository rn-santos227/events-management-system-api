package com.mgnt.events.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mgnt.events.security.PrivilegeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  private final @NonNull PrivilegeInterceptor privilegeInterceptor;

  public WebConfig(@NonNull PrivilegeInterceptor privilegeInterceptor) {
    this.privilegeInterceptor = privilegeInterceptor;
  }

  @Override
  public void addInterceptors(@NonNull InterceptorRegistry registry) {
    registry.addInterceptor(privilegeInterceptor);
  }
}
