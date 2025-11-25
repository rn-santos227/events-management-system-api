package com.mgnt.events.cli;

import java.util.Objects;

import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLNamedOutputType;
import graphql.schema.GraphQLType;

public class RouteListCommand {
  private RouteListCommand() {}

  public static void main(String[] args) {

  }

  private static void printRestRoutes(RequestMappingHandlerMapping mapping) {
    Objects.requireNonNull(mapping, "Request mapping handler must not be null");
  }

  private static void printGraphqlRoutes(GraphQlSource graphQlSource) {

  }

  private static String formatGraphqlArgument(GraphQLArgument argument) {
    return "%s: %s".formatted(argument.getName(), resolveTypeName(argument.getType()));
  }

  private static String resolveTypeName(GraphQLType type) {
    if (type instanceof GraphQLNamedOutputType namedType) {
      return namedType.getName();
    }
    return type.toString();
  }

  private record RouteDetail(String type, String method, String path, String handler) {}
}
