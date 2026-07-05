package com.machine.sdk.base.config;

import com.machine.sdk.base.context.FeignRequestInterceptor;
import com.machine.sdk.base.envm.base.EnvironmentEnum;
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
public class OpenFeignMidTimeConfig {

    @Value("${spring.profiles.active}")
    private EnvironmentEnum environmentEnum;

    @Bean
    public Logger.Level feignLoggerLevel() {
        return switch (environmentEnum) {
            case LOCAL, DEV -> Logger.Level.BASIC;
            case SIT, TEST, UAT -> Logger.Level.BASIC;
            case PET, SIM, PROD -> Logger.Level.BASIC;
        };
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(5L, TimeUnit.SECONDS,
                30 * 60L, TimeUnit.SECONDS, true);
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
