package com.machine.service.scm;

import com.machine.sdk.common.config.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients(basePackages = {
        "com.machine.client"
})
@SpringBootApplication(scanBasePackages = {
        "com.machine.starter",
        "com.machine.service.scm"
})
@Import({WebMvcConfig.class})
public class ScmServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(ScmServiceApp.class, args);
    }

}
