package com.machine.starter.sdk.config;

import com.machine.sdk.beisen.interceptor.BeiSenTokenInterceptor;
import com.machine.sdk.common.tool.OkHttp3Util;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeiSenSdkHttpConfig {

    @Value("${beisen.appKey:XXX}")
    private String appKey;

    @Value("${beisen.appSecret:XXX}")
    private String appSecret;

    @Bean(name = "beiSenOkHttpClient")
    public OkHttpClient listenerContainer() {
        return OkHttp3Util.getOkHttpClientBuilder()
                .addInterceptor(new BeiSenTokenInterceptor(appKey,appSecret))
                .build();
    }
}
