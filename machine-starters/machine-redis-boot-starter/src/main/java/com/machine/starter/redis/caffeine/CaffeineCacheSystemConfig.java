package com.machine.starter.redis.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.machine.client.data.config.IDataOpenapiConfigClient;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaffeineCacheSystemConfig {

    @Resource(name = "systemConfigCaffeine")
    private Cache<String, Object> caffeineCache;

    @Autowired
    private IDataOpenapiConfigClient openapiConfigClient;

}
