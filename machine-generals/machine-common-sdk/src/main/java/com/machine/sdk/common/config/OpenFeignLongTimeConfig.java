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
public class OpenFeignLongTimeConfig {

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
        return Retryer.NEVER_RETRY;
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(5L, TimeUnit.SECONDS,
                2 * 60 * 60L, TimeUnit.SECONDS, false);
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
