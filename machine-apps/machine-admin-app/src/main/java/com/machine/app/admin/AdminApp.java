package com.machine.app.admin;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFileStorage
@EnableDiscoveryClient
@EnableFeignClients(
        basePackages = "com.machine.client"
)
@SpringBootApplication(scanBasePackages = {
        "com.machine.starter",
        "com.machine.app.admin"
})
public class AdminApp {

    static void main(String[] args) {
        SpringApplication.run(AdminApp.class, args);
    }

}