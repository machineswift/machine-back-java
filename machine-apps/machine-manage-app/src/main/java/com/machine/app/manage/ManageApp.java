package com.machine.app.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = {
        "com.machine.client",
})
@SpringBootApplication(scanBasePackages = {
        "com.machine.starter",
        "com.machine.app.manage"
})
public class ManageApp {

    public static void main(String[] args) {
        SpringApplication.run(ManageApp.class, args);
    }

}