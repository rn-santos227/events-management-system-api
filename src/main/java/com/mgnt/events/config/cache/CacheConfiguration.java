package com.mgnt.events.config.cache;

import java.time.Duration;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.mgnt.events.constants.Config;

@Configuration
@EnableCaching
public class CacheConfiguration {
  @Bean
  public CacheManager cacheManager(
    RedisConnectionFactory connectionFactory,
    @Value(Config.CACHE_TTL) long timeToLiveMillis
  ) {
    RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
      .defaultCacheConfig()
      .entryTtl(Objects.requireNonNull(Duration.ofMillis(timeToLiveMillis)));

  }
}
