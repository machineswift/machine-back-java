package com.machine.sdk.common.config;

import com.machine.sdk.common.context.FeignRequestInterceptor;
import com.machine.sdk.common.envm.system.EnvironmentEnum;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.InvalidParameterException;
import java.util.concurrent.TimeUnit;


@Configuration
public class OpenFeignDefaultConfig {

    @Value("${spring.profiles.active}")
    private EnvironmentEnum environmentEnum;

    @Bean
    public Logger.Level feignLoggerLevel() {
        return switch (environmentEnum) {
            case LOCAL, DEV, SIT -> Logger.Level.FULL;
            case UAT -> Logger.Level.HEADERS;
            case PET, SIM, PROD -> Logger.Level.BASIC;
            default -> throw new InvalidParameterException("未选择环境");
        };
    }

    @Bean
    public Retryer feignRetryer() {
        //最大请求次数为5，初始间隔时间为100ms，下次间隔时间1.5倍递增，重试间最大间隔时间为1s。
        return new Retryer.Default();
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(5L, TimeUnit.SECONDS,
                30L, TimeUnit.SECONDS, true);
    }

    @Bean
    public feign.codec.ErrorDecoder errorDecoder() {
        return new ErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
