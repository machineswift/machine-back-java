package com.machine.starter.web.config;

import com.machine.client.iam.biam.log.IBIamOperationLogClient;
import com.machine.client.iam.biam.log.IBIamUserAccessLogClient;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.starter.web.WebProperties;
import com.machine.starter.web.operateLog.OperationLogAspect;
import com.machine.starter.web.operateLog.OperationLogListener;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 操作日志自动装配。
 */
@Configuration(proxyBeanMethods = false)
public class OperationLogConfiguration {

    /**
     * 操作日志异步落库线程池
     */
    @Bean
    @ConditionalOnMissingBean(name = "operationLogExecutor")
    public ThreadPoolTaskExecutor operationLogExecutor(WebProperties properties) {
        WebProperties.OperationLog operationLog = properties.getOperationLog();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(operationLog.getCorePoolSize());
        executor.setMaxPoolSize(operationLog.getMaxPoolSize());
        executor.setQueueCapacity(operationLog.getQueueCapacity());
        executor.setKeepAliveSeconds(operationLog.getKeepAliveSeconds());
        executor.setThreadNamePrefix("operation-log-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 业务快照加载器注册表
     */
    @Bean
    @ConditionalOnMissingBean
    public OperationLogLoaderRegistry operationLogLoaderRegistry() {
        return new OperationLogLoaderRegistry();
    }

    /**
     * 操作日志监听器
     */
    @Bean
    @ConditionalOnMissingBean
    public OperationLogListener operationLogListener(ObjectProvider<IBIamUserClient> biamUserProvider,
                                                     ObjectProvider<IBIamUserAccessLogClient> accessLogClientProvider,
                                                     ObjectProvider<IBIamOperationLogClient> operationLogClientProvider) {
        return new OperationLogListener(biamUserProvider, accessLogClientProvider, operationLogClientProvider);
    }

    /**
     * 操作日志切面
     */
    @Bean
    @ConditionalOnMissingBean
    public OperationLogAspect operationLogAspect(WebProperties properties,
                                                 ApplicationEventPublisher eventPublisher,
                                                 OperationLogLoaderRegistry loaderRegistry) {
        return new OperationLogAspect(properties, eventPublisher, loaderRegistry);
    }
}
