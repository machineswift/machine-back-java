package com.machine.starter.web.config;

import com.machine.client.iam.biam.log.IBIamUserAccessLogClient;
import com.machine.starter.redis.RedisAutoConfiguration;
import com.machine.starter.web.WebProperties;
import com.machine.starter.web.accessLog.ApiAccessLogAspect;
import com.machine.starter.web.accessLog.ApiAccessLogFilter;
import com.machine.starter.web.accessLog.ApiAccessLogListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class WebAccessLogConfiguration {

    /**
     * 访问日志异步落库线程池
     */
    @Bean
    @ConditionalOnMissingBean(name = "apiAccessLogExecutor")
    public ThreadPoolTaskExecutor apiAccessLogExecutor(WebProperties properties) {
        WebProperties.AccessLog accessLog = properties.getAccessLog();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(accessLog.getCorePoolSize());
        executor.setMaxPoolSize(accessLog.getMaxPoolSize());
        executor.setQueueCapacity(accessLog.getQueueCapacity());
        executor.setKeepAliveSeconds(accessLog.getKeepAliveSeconds());
        executor.setThreadNamePrefix("api-access-log-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 访问日志监听器
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiAccessLogListener apiAccessLogListener(ObjectProvider<IBIamUserAccessLogClient> accessLogClientProvider) {
        return new ApiAccessLogListener(accessLogClientProvider);
    }

    /**
     * 访问日志切面
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiAccessLogAspect apiAccessLogAspect(WebProperties properties,
                                                 ApplicationEventPublisher applicationEventPublisher) {
        return new ApiAccessLogAspect(properties, applicationEventPublisher);
    }

    /**
     * 访问日志请求包装过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiAccessLogFilter apiAccessLogFilter(WebProperties properties) {
        return new ApiAccessLogFilter(properties);
    }

}


