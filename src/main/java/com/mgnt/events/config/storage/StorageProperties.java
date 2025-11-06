package com.mgnt.events.config.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import com.mgnt.events.constants.Storage;
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = Storage.PROPERTY_PREFIX)
public class StorageProperties {
  private StorageProvider provider = StorageProvider.S3;
  private String accessKey;
  private String bucket;
  private String secretKey;
  private String endpoint;
  private boolean enabled;
}
