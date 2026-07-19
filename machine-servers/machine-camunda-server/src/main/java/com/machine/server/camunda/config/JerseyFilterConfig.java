package com.machine.server.camunda.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
@ConditionalOnClass(ResourceConfig.class)
public class JerseyFilterConfig {

    private final ResourceConfig resourceConfig;

    public JerseyFilterConfig(ResourceConfig resourceConfig) {
        this.resourceConfig = resourceConfig;
    }

    @PostConstruct
    public void init() {
        resourceConfig.register(EncodingResponseFilter.class);
    }
}