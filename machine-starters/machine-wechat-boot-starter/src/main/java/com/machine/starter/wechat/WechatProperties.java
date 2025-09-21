package com.machine.starter.wechat;

import com.machine.sdk.common.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:machine-wechat.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "machine.wechat")
public class WechatProperties {
}