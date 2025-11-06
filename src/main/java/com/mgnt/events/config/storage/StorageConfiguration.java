package com.mgnt.events.config.storage;

import java.net.URI;
import java.util.Objects;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mgnt.events.constants.Storage;

import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
@ConditionalOnProperty(
  prefix = Storage.PROPERTY_PREFIX,
  name = Storage.ENABLED_PROPERTY_NAME,
  havingValue = Storage.HAVING_VALUE
)
public class StorageConfiguration {

  @Bean
  public S3Client s3Client(StorageProperties properties) {
    String accessKey = requireProperty(properties.getAccessKey(), Storage.ACCESS_KEY_PROPERTY);
    String secretKey = requireProperty(properties.getSecretKey(), Storage.SECRET_KEY_PROPERTY);
  }
  
  private String requireProperty(String value, String propertyName) {
    if (Objects.requireNonNullElse(value, "").isBlank()) {
      throw new IllegalStateException(
        String.format("Property '%s' must be configured when storage is enabled", propertyName)
      );
    }
    return value;
  }
}
