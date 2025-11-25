package com.mgnt.events.cli;

import java.util.Objects;

import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class RouteListCommand {
  private RouteListCommand() {}

  public static void main(String[] args) {

  }

  private static void printRestRoutes(RequestMappingHandlerMapping mapping) {
    Objects.requireNonNull(mapping, "Request mapping handler must not be null");
  }

  private static void printGraphqlRoutes(GraphQlSource graphQlSource) {

  }


  private record RouteDetail(String type, String method, String path, String handler) {}
}
