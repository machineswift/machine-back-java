package com.machine.starter.sdk.config;

import com.machine.sdk.common.tool.OkHttp3Util;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SelfSdkHttpConfig {

    @Bean(name = "selfWebHookSdkHttpClient")
    public OkHttpClient listenerContainer() {
        return OkHttp3Util.getOkHttpClientBuilder()
                .build();
    }
}
