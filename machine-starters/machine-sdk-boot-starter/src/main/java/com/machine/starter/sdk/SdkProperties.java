package com.machine.starter.sdk;

import com.machine.sdk.common.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:machine-sdk.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "machine.sdk")
public class SdkProperties {
    private String example;
}