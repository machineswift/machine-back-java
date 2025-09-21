package com.machine.starter.obs;

import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
public class ObsAutoConfiguration {

    private final ObsProperties obsProperties;

    public ObsAutoConfiguration(ObsProperties obsProperties) {
        this.obsProperties = obsProperties;
    }

}

