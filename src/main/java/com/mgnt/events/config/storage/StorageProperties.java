package com.mgnt.events.config.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import com.mgnt.events.constants.Storage;

@Validated
@ConfigurationProperties(prefix = Storage.PROPERTY_PREFIX)
public class StorageProperties {
  private StorageProvider _provider = StorageProvider.S3;
  private String _accessKey;
  private String _bucket;
  private String _secretKey;
  private String _endpoint;
  private boolean _enabled;
}
