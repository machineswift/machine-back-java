package com.machine.app.xxljob;

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
        "com.machine.app.xxljob"
})
public class XxljobApp {

    public static void main(String[] args) {
        SpringApplication.run(XxljobApp.class, args);
    }

}
