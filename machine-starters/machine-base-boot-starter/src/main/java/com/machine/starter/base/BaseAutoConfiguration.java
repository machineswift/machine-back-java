package com.machine.starter.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@EnableAspectJAutoProxy
@Configuration(proxyBeanMethods = false)
public class BaseAutoConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .changeDefaultPropertyInclusion(
                        incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL)
                                .withContentInclusion(JsonInclude.Include.NON_NULL)
                )
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement(
                "",
                DataSize.ofGigabytes(1).toBytes(),
                DataSize.ofGigabytes(4).toBytes(),
                (int) DataSize.ofMegabytes(1).toBytes());
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}