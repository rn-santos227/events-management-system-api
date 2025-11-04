package com.mgnt.events.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.graphql.data.method.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mgnt.events.security.annotations.RequiresPrivilege;

import io.micrometer.common.lang.Nullable;

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
