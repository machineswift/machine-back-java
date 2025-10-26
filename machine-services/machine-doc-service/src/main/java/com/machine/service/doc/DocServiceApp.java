package com.machine.service.doc;

import com.machine.sdk.common.config.CustomerWebMvcConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {
        "com.machine.client"
})
@SpringBootApplication(scanBasePackages = {
        "com.machine.starter",
        "com.machine.service.doc"
})
@Import({CustomerWebMvcConfigurer.class})
public class DocServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(DocServiceApp.class, args);
    }

}
