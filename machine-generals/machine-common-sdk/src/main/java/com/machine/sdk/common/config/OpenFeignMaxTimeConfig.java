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
public class OpenFeignMaxTimeConfig {

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
        return Retryer.NEVER_RETRY;
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(5L, TimeUnit.SECONDS,
                2 * 3600L, TimeUnit.SECONDS, false);
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
