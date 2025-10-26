package com.machine.service.plugin;

import com.machine.sdk.common.config.CustomerWebMvcConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients(basePackages = {
        "com.machine.client"
})
@SpringBootApplication(scanBasePackages = {
        "com.machine.starter",
        "com.machine.service.plugin"
})
@Import({CustomerWebMvcConfigurer.class})
public class PluginServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(PluginServiceApp.class, args);
    }

}
