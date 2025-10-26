package com.machine.starter.ai;

import com.machine.sdk.common.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:machine-ai.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "machine.ai")
public class AiProperties {
}