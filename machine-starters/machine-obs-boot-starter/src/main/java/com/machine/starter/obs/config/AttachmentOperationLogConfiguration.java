package com.machine.starter.obs.config;

import com.machine.client.data.filecenter.attachment.IDataAttachmentClient;
import com.machine.client.data.filecenter.attachment.IDataAttachmentOperationLogClient;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.starter.obs.ObsProperties;
import com.machine.starter.obs.operateLog.AttachmentOperationLogListener;
import com.machine.starter.obs.operateLog.AttachmentOperationLogPublisher;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 附件操作日志自动装配（注解 + 事件 + 异步落库）。
 */
@Configuration(proxyBeanMethods = false)
public class AttachmentOperationLogConfiguration {

    /**
     * 附件操作日志异步落库线程池
     */
    @Bean
    @ConditionalOnMissingBean(name = "attachmentOperationLogExecutor")
    public ThreadPoolTaskExecutor attachmentOperationLogExecutor(ObsProperties properties) {
        ObsProperties.AttachmentOperationLog attachmentOperationLog = properties.getAttachmentOperationLog();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(attachmentOperationLog.getCorePoolSize());
        executor.setMaxPoolSize(attachmentOperationLog.getMaxPoolSize());
        executor.setQueueCapacity(attachmentOperationLog.getQueueCapacity());
        executor.setKeepAliveSeconds(attachmentOperationLog.getKeepAliveSeconds());
        executor.setThreadNamePrefix("attachment-operation-log-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 附件操作日志发布器
     */
    @Bean
    @ConditionalOnMissingBean
    public AttachmentOperationLogPublisher attachmentOperationLogPublisher(ApplicationEventPublisher eventPublisher,
                                                                           ObjectProvider<IDataAttachmentClient> attachmentProvider) {
        return new AttachmentOperationLogPublisher(eventPublisher, attachmentProvider);
    }

    /**
     * 附件操作日志监听器
     */
    @Bean
    @ConditionalOnMissingBean
    public AttachmentOperationLogListener attachmentOperationLogListener(ObjectProvider<IDataAttachmentOperationLogClient> operationLogClientProvider,
                                                                         ObjectProvider<IBIamUserClient> userProvider) {
        return new AttachmentOperationLogListener(operationLogClientProvider, userProvider);
    }

}
