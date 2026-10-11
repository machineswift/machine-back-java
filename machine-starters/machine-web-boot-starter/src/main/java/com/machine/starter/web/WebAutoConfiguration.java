package com.machine.starter.web;

import com.machine.starter.redis.RedisAutoConfiguration;
import com.machine.starter.web.operateLog.annotation.WebOperationLog;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


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

    /**
     * 启动期自检：diff 已开启但未注册业务快照加载器的接口（注册 key = moduleEntity + 控制器方法名）。
     */
    @EventListener(ApplicationReadyEvent.class)
    public void verifyOperationLogLoaders(ApplicationReadyEvent event) {
        if (!properties.getOperationLog().isEnabled()) {
            return;
        }
        ApplicationContext context = event.getApplicationContext();
        OperationLogLoaderRegistry loaderRegistry =
                context.getBeanProvider(OperationLogLoaderRegistry.class).getIfAvailable();
        if (loaderRegistry == null) {
            return;
        }

        List<String> missingList = new ArrayList<>();
        for (Object bean : context.getBeansWithAnnotation(RestController.class).values()) {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            if (targetClass == null) {
                continue;
            }
            for (Method method : targetClass.getDeclaredMethods()) {
                WebOperationLog operationLog =
                        AnnotatedElementUtils.findMergedAnnotation(method, WebOperationLog.class);
                if (operationLog == null || !operationLog.diff()) {
                    continue;
                }
                if (loaderRegistry.get(operationLog.moduleEntity(), method.getName()) == null) {
                    missingList.add(targetClass.getSimpleName() + "#" + method.getName()
                            + "(" + operationLog.moduleEntity() + ")");
                }
            }
        }
        if (!missingList.isEmpty()) {
            log.warn("以下接口已开启操作日志 diff 但未注册业务快照加载器，diff 将为空，请在对应的 *OperateLogLoad 中注册：{}",
                    missingList);
        }
    }
}


