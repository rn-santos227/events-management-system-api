package com.mgnt.events.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.graphql.data.method.HandlerMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mgnt.events.security.annotations.RequiresPrivilege;

@Component
public class PrivilegeInterceptor extends HandlerInterceptor {
  @Override
  public boolean preHandle(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull Object handler
  ) throws Exception {
    if (!(handler instanceof HandlerMethod handlerMethod)) {
      return true;
    }

    RequiresPrivilege requiresPrivilege = resolveAnnotation(handlerMethod);
    if (requiresPrivilege == null) {
      return true;
    }
  }

  private void ensureAuthenticated(String[] requiredPrivileges) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
    }

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Set<String> granted = authorities
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toSet());
  }

  @Nullable
  private RequiresPrivilege resolveAnnotation(HandlerMethod handlerMethod) {
    RequiresPrivilege methodAnnotation = handlerMethod.getMethodAnnotation(RequiresPrivilege.class);
    if (methodAnnotation != null) {
      return methodAnnotation;
    }

    Class<?> beanType = handlerMethod.getBeanType();
    return beanType.getAnnotation(RequiresPrivilege.class);
  }
}
