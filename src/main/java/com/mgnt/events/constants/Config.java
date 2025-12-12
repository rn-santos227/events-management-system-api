package com.mgnt.events.constants;

public final class Config {
  private Config() {}
  
  public static final String CACHE_TTL = "${spring.cache.redis.time-to-live:300000}";
}
