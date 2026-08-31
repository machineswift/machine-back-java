package com.machine.starter.web;

import com.machine.starter.redis.RedisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;


@Slf4j
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class WebAutoConfiguration {

    private final WebProperties properties;

    public WebAutoConfiguration(WebProperties properties) {
        this.properties = properties;
    }

    /**
     * 启动期自检
     */
    @EventListener(ApplicationReadyEvent.class)
    public void verifyAsyncEnabled(ApplicationReadyEvent event) {
        if (!properties.getAccessLog().isEnabled()) {
            return;
        }
        ApplicationContext context = event.getApplicationContext();
        if (context.getBeansOfType(AsyncAnnotationBeanPostProcessor.class).isEmpty()) {
            log.warn("检测到未启用 @EnableAsync：访问日志将同步落库并阻塞请求线程。"
                    + "请在应用启动类（@SpringBootApplication 所在类）上添加 @EnableAsync。");
        }
    }
}


