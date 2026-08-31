package com.machine.starter.redis.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.machine.sdk.base.model.dto.biam.identity.BIamOAuth2RegisteredClientDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration(proxyBeanMethods = false)
public class CaffeineCacheConfig {

    /**
     * 缓存 t_oauth2_registered_client 版本数据
     */
    @Bean(name = "oauth2RegisteredClientVersionCaffeine")
    public Cache<String, Long> oauth2RegisteredClientVersionCaffeine() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .initialCapacity(64)
                .maximumSize(8192)
                .build();
    }

    /**
     * 缓存 t_oauth2_registered_client 数据
     */
    @Bean(name = "oauth2RegisteredClientDataCaffeine")
    public Cache<String, BIamOAuth2RegisteredClientDto> oauth2RegisteredClientDataCaffeine() {
        return Caffeine.newBuilder()
                .expireAfterWrite(6, TimeUnit.HOURS)
                .initialCapacity(64)
                .maximumSize(8192)
                .build();
    }

    /**
     * 缓存启用状态的客户端ID列表（webhook 广播用，短 TTL 控制新鲜度）
     */
    @Bean(name = "registeredClientIdsCaffeine")
    public Cache<String, List<String>> registeredClientIdsCaffeine() {
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .initialCapacity(8)
                .maximumSize(64)
                .build();
    }

    /**
     * 缓存 system_config 数据
     */
    @Bean(name = "systemConfigCaffeine")
    public Cache<String, Object> systemConfigCaffeine() {
        return Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .initialCapacity(64)
                .maximumSize(8192)
                .build();
    }

}

