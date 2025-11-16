package com.mgnt.events.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mgnt.events.security.PrivilegeInterceptor;
import com.mgnt.events.constants.Profiles;

@Configuration
@Profile(Profiles.GLOBAL)
public class WebConfiguration implements WebMvcConfigurer {
  private final @NonNull PrivilegeInterceptor privilegeInterceptor;

  public WebConfiguration(@NonNull PrivilegeInterceptor privilegeInterceptor) {
    this.privilegeInterceptor = privilegeInterceptor;
  }

  @Override
  public void addInterceptors(@NonNull InterceptorRegistry registry) {
    registry.addInterceptor(privilegeInterceptor);
  }
}
