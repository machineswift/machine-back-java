package com.machine.starter.mybatis;

import com.machine.sdk.common.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:machine-mybatis.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "machine.mybatis")
public class MybatisProperties {
    private String example;
}