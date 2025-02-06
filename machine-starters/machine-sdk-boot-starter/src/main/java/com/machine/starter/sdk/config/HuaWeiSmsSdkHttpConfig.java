package com.machine.starter.sdk.config;

import com.machine.sdk.huawei.client.sms.HuaWeiSmsHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HuaWeiSmsSdkHttpConfig {

    @Value("${huaweiyun.sms.appKey:XXX}")
    private String appKey;

    @Value("${huaweiyun.sms.appSecret:XXX}")
    private String appSecret;

    @Value("${huaweiyun.sms.sender.captcha:XXX}")
    private String captchaSender;

    @Value("${huaweiyun.sms.sender.message:XXX}")
    private String messageSender;

    @Bean(name = "huaWeiSmsCaptchaHttpClient")
    public HuaWeiSmsHttpClient huaWeiSmsCaptchaHttpClient() {
        return new HuaWeiSmsHttpClient(appKey, appSecret, captchaSender);
    }

    @Bean(name = "huaWeiSmsMessageHttpClient")
    public HuaWeiSmsHttpClient huaWeiSmsMessageHttpClient() {
        return new HuaWeiSmsHttpClient(appKey, appSecret, messageSender);
    }
}
