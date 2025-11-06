package com.mgnt.events.config.storage;

import java.net.URI;
import java.util.Objects;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mgnt.events.constants.Storage;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
@ConditionalOnProperty(
  prefix = Storage.PROPERTY_PREFIX,
  name = Storage.ENABLED_PROPERTY_NAME,
  havingValue = Storage.HAVING_VALUE
)
public class StorageConfiguration {

  
  private String requireProperty(String value, String propertyName) {
    if (Objects.requireNonNullElse(value, "").isBlank()) {
      throw new IllegalStateException(
        String.format("Property '%s' must be configured when storage is enabled", propertyName)
      );
    }
    return value;
  }
}
