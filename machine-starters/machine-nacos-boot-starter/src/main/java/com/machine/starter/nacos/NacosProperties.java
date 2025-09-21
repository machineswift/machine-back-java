package com.machine.starter.nacos;

import com.machine.sdk.common.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:machine-nacos.yaml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "machine.nacos")
public class NacosProperties {
    private String example;
}