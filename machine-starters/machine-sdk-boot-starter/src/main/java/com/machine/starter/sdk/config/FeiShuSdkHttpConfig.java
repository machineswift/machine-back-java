package com.machine.starter.sdk.config;

import com.machine.sdk.feishu.client.FeiShuMessageHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeiShuSdkHttpConfig {

    @Value("${feiShu.message.appId:XXX}")
    private String appId;

    @Value("${feiShu.message.appSecret:XXX}")
    private String appSecret;

    @Bean(name = "feiShuMessageHttpClient")
    public FeiShuMessageHttpClient feiShuHttpClient() {
        return new FeiShuMessageHttpClient(appId, appSecret);
    }
}
