package com.mgnt.events.cli;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.mgnt.events.constants.Patterns;
import com.mgnt.events.util.RequestValidators;

import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNamedOutputType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;

public class RouteListCommand {
  private RouteListCommand() {}

  public static void main(String[] args) {

  }

  private static void printRestRoutes(RequestMappingHandlerMapping mapping) {
    Objects.requireNonNull(mapping, "Request mapping handler must not be null");
  }
  
  private static Set<String> extractPaths(RequestMappingInfo info) {
    Objects.requireNonNull(info, "Request mapping info must not be null");

    var patternsCondition = info.getPatternsCondition();
    if (patternsCondition == null || patternsCondition.getPatterns() == null) {
      return Collections.emptySet();
    }

    return patternsCondition
      .getPatterns()
      .stream()
      .map(Object::toString)
      .collect(Collectors.toSet());
  }

  private static Set<String> extractMethods(RequestMappingInfo info) {
    Objects.requireNonNull(info, "Request mapping info must not be null");

    return info
      .getMethodsCondition()
      .getMethods()
      .stream()
      .map(Enum::name)
      .collect(Collectors.toSet());
  }

  private static void printGraphqlRoutes(GraphQlSource graphQlSource) {
    Objects.requireNonNull(graphQlSource, "GraphQL source must not be null");

    GraphQLSchema schema = graphQlSource.schema();
    GraphQLObjectType queryType = schema.getQueryType();
    if (queryType == null) {
      System.out.println("No GraphQL queries configured.");
      return;
    }

    List<String> lines = queryType
      .getFieldDefinitions()
      .stream()
      .sorted(Comparator.comparing(GraphQLFieldDefinition::getName))
      .map(RouteListCommand::formatGraphqlField)
      .toList();

    System.out.println("GraphQL Queries");
    System.out.println("---------------");
    lines.forEach(System.out::println);
  }

  private static String formatGraphqlField(GraphQLFieldDefinition field) {
    String arguments = field
      .getArguments()
      .stream()
      .map(RouteListCommand::formatGraphqlArgument)
      .collect(Collectors.joining(", "));

    String returnType = resolveTypeName(field.getType());
    return Patterns.GRAPHQL_ROUTE_PATTERN.formatted(field.getName(), arguments, returnType);
  }

  private static String formatGraphqlArgument(GraphQLArgument argument) {
    return Patterns.GRAPHQL_TYPE_PATTERN.formatted(argument.getName(), resolveTypeName(argument.getType()));
  }

  private static String resolveTypeName(GraphQLType type) {
    if (type instanceof GraphQLNamedOutputType namedType) {
      return namedType.getName();
    }
    return type.toString();
  }

  private record RouteDetail(String type, String method, String path, String handler) {}
}
