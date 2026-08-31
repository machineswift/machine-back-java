package com.machine.starter.web;

import com.machine.sdk.base.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:machine-web.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "machine.web")
public class WebProperties {

    /**
     * 访问日志配置
     */
    private AccessLog accessLog = new AccessLog();

    /**
     * 操作日志配置
     */
    private OperationLog operationLog = new OperationLog();

    @Data
    public static class AccessLog {

        /**
         * 是否启用访问日志（全局开关）
         */
        private boolean enabled = true;

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

        /**
         * 设备ID请求头名称（兼容从 Header / Query 读取）
         */
        private String deviceIdHeader = "deviceId";

        /**
         * 扩展信息请求头名称列表
         */
        private String[] extendHeaders = {};
    }

    @Data
    public static class OperationLog {

        /**
         * 是否启用操作日志（全局开关）
         */
        private boolean enabled = true;

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

        /**
         * 设备ID请求头名称（兼容从 Header / Query 读取）
         */
        private String deviceIdHeader = "deviceId";

        /**
         * 扩展信息请求头名称列表
         */
        private String[] extendHeaders = {};
    }
}