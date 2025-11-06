package com.mgnt.events.config.storage;

import java.net.URI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;

import com.mgnt.events.constants.Storage;
import com.mgnt.events.utils.RequestValidators;

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
    String _accessKey = requireProperty(properties.getAccessKey(), Storage.ACCESS_KEY_PROPERTY);
    String _secretKey = requireProperty(properties.getSecretKey(), Storage.SECRET_KEY_PROPERTY);

    AwsBasicCredentials _credentials = AwsBasicCredentials.create(_accessKey, _secretKey);
    S3ClientBuilder builder = S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(_credentials));

    String regionValue = properties.getRegion();
    Region region = regionValue == null || regionValue.isBlank()
      ? Region.US_EAST_1 : Region.of(regionValue);
    builder.region(region);
    
    if (properties.getEndpoint() != null && !properties.getEndpoint().isBlank()) {
      builder.endpointOverride(URI.create(properties.getEndpoint()));
    }

    boolean usePathStyle = properties.getProvider() == StorageProvider.MINIO || properties.isPathStyleAccess();
      if (usePathStyle) {
      builder.serviceConfiguration(
        S3Configuration.builder().pathStyleAccessEnabled(true).build()
      );
    }

    return builder.build();
  }
  
  private String requireProperty(String value, String propertyName) {
    if (RequestValidators.isBlank(value)) {
      throw new IllegalStateException(
        String.format("Property '%s' must be configured when storage is enabled", propertyName)
      );
    }
    return value;
  }
}
