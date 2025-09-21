package com.machine.sdk.common.config;

import com.machine.sdk.common.context.FeignRequestInterceptor;
import com.machine.sdk.common.envm.system.SystemEnvironmentEnum;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
public class OpenFeignDefaultConfig {

    @Value("${spring.profiles.active}")
    private SystemEnvironmentEnum environmentEnum;

    @Bean
    public Logger.Level feignLoggerLevel() {
        return switch (environmentEnum) {
            case LOCAL, DEV -> Logger.Level.HEADERS;
            case SIT, TEST, UAT -> Logger.Level.HEADERS;
            case PET, SIM, PROD -> Logger.Level.BASIC;
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
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
