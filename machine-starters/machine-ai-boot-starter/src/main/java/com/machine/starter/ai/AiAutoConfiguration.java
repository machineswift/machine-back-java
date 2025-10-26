package com.machine.starter.ai;

import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class AiAutoConfiguration {

    private final AiProperties aiProperties;

    public AiAutoConfiguration(AiProperties aiProperties) {
        this.aiProperties = aiProperties;
    }

}

