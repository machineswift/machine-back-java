package com.machine.starter.obs;

import com.machine.sdk.base.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:machine-obs.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "machine.obs")
public class ObsProperties {

    /**
     * 附件操作日志配置
     */
    private AttachmentOperationLog attachmentOperationLog = new AttachmentOperationLog();

    @Data
    public static class AttachmentOperationLog {

        /**
         * 异步落库线程池核心线程数
         */
        private int corePoolSize = 2;

        /**
         * 异步落库线程池最大线程数
         */
        private int maxPoolSize = 8;

        /**
         * 异步落库线程池队列容量
         */
        private int queueCapacity = 64 * 1024;

        /**
         * 线程空闲存活时间（秒）
         */
        private int keepAliveSeconds = 5 * 60;
    }
}