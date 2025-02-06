package com.machine.starter.sdk.config;

import com.machine.sdk.huawei.client.file.HuaWeiObsHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HuaWeiObsSdkHttpConfig {

    @Value("${huawei.obs.ak:XXX}")
    private String ak;

    @Value("${huawei.obs.sk:XXX}")
    private String sk;

    @Value("${huawei.obs.upload.endPoint:XXX}")
    private String endPoint;

    @Value("${huawei.obs.upload.bucketName:machine-app-file}")
    private String bucketName;

    @Value("${huawei.obs.upload.parentDir:machine}")
    private String parentDir;

    //@Value("${huawei.obs.upload.expires}")
    private Integer expires = 180;

    @Bean(name = "huaWeiObsHttpClient")
    public HuaWeiObsHttpClient huaWeiObsHttpClient() {
        return new HuaWeiObsHttpClient(ak, sk, endPoint, bucketName, parentDir, expires);
    }


}