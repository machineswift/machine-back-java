package com.machine.starter.redis;

import com.machine.sdk.common.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:machine-redis.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "machine.redis")
public class RedisProperties {
    private String host;
    private Integer port;
    private String password;
}