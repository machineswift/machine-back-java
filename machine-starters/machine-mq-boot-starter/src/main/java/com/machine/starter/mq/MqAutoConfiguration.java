package com.machine.starter.mq;

import com.machine.client.iam.auth.IIamOauth2RegisteredClientClient;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.cache.LocalCacheRegisteredClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class MqAutoConfiguration {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private IIamOauth2RegisteredClientClient oauth2RegisteredClientClient;

    @Bean(name = "customerStreamBridge")
    public CustomerStreamBridge customerStreamBridge(LocalCacheRegisteredClient localCacheRegisteredClient) {
        return new CustomerStreamBridge(streamBridge, localCacheRegisteredClient, oauth2RegisteredClientClient);
    }

}

