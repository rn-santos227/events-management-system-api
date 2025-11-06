package com.mgnt.events.config.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import com.mgnt.events.constants.Storage;

@Validated
@ConfigurationProperties(prefix = Storage.PROPERTY_PREFIX)
public class StorageProperties {
    
}
