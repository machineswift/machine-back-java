package com.machine.starter.mq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可靠消息注解类
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MqReliableMessageAnnotation {
    /**
     * 消费者名称
     */
    String consumerName();

    /**
     * 重试策略
     */
    int[] retryStrategy() default {5, 30, 120};
}
