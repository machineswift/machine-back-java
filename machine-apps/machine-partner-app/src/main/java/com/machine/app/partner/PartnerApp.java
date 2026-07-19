package com.machine.app.partner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {
        "com.machine.client",
})
@SpringBootApplication(scanBasePackages = {
        "com.machine.starter",
        "com.machine.app.partner"
})
public class PartnerApp {

    static void main(String[] args) {
        SpringApplication.run(PartnerApp.class, args);
    }

}