package com.machine.service.crm;

import com.machine.sdk.common.config.WebMvcConfig;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFileStorage
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients(basePackages = {
        "com.machine.client"
})
@SpringBootApplication(scanBasePackages = {
        "com.machine.starter",
        "com.machine.service.crm"
})
@Import({WebMvcConfig.class})
public class CrmServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(CrmServiceApp.class, args);
    }

}
