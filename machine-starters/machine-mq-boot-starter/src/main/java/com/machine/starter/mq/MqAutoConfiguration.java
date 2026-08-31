package com.machine.starter.mq;

import com.machine.client.iam.biam.identity.IBIamOauth2RegisteredClientClient;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.caffeine.CaffeineCacheRegisteredClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class MqAutoConfiguration {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private IBIamOauth2RegisteredClientClient oauth2RegisteredClientClient;

    @Bean(name = "customerStreamBridge")
    public CustomerStreamBridge customerStreamBridge(CaffeineCacheRegisteredClient caffeineCacheRegisteredClient) {
        return new CustomerStreamBridge(streamBridge, caffeineCacheRegisteredClient, oauth2RegisteredClientClient);
    }

}

