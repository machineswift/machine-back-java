package com.machine.sdk.common.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;
import java.util.Properties;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @NotNull
    @Override
    public PropertySource<?> createPropertySource(String name,
                                                  EncodedResource encodedResource) {
        Resource resource = encodedResource.getResource();
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource);
        Properties props = factory.getObject();
        String sourceName = name != null ? name : resource.getFilename();
        return new PropertiesPropertySource(
                Objects.requireNonNull(sourceName, "Property source name cannot be null"),
                Objects.requireNonNull(props, "Properties cannot be null")
        );
    }
}