package com.machine.starter.security;

import com.machine.starter.security.util.MachineJwtUtil;
import com.machine.starter.security.util.RsaKeyUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration(proxyBeanMethods = false)
public class SecurityAutoConfiguration {

    @Value("${machine.jwt.rsa.public-key}")
    private String rsaPublicKey;

    @Value("${machine.jwt.rsa.private-key}")
    private String rsaPrivateKey;

    @Bean
    public MachineJwtUtil getmachineJwtUtil() {
        RSAPublicKey publicKey = RsaKeyUtil.parsePublicKey(rsaPublicKey);
        RSAPrivateKey privateKey = RsaKeyUtil.parsePrivateKey(rsaPrivateKey);
        return new MachineJwtUtil(publicKey, privateKey);
    }

}