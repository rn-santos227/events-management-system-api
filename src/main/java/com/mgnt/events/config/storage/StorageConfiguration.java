package com.mgnt.events.config.storage;

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
    return value;
  }
}
