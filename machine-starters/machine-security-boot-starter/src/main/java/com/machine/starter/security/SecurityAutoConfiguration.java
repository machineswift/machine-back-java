package com.machine.starter.security;

import com.machine.starter.security.util.MachineJwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class SecurityAutoConfiguration {

    @Value("${machine.jwt.secret:XXX}")
    private String secret;

    @Bean
    public MachineJwtUtil getmachineJwtUtil() {
        return new MachineJwtUtil(secret);
    }

}