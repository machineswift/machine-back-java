package com.machine.starter.common;

import com.machine.sdk.common.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:machine-common.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "machine.common")
public class CommonProperties {
}