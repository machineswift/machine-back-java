package com.machine.starter.common;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.unit.DataSize;

@EnableAspectJAutoProxy
@Configuration(proxyBeanMethods = false)
public class CommonAutoConfiguration {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置最大文件大小
        factory.setMaxFileSize(DataSize.ofGigabytes(2));
        // 设置总上传大小
        factory.setMaxRequestSize(DataSize.ofGigabytes(8));
        return factory.createMultipartConfig();
    }

}

