package com.machine.starter.security.config.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

@Component
@Slf4j
public class CustomerKeyService {

    @Value("${token.private-key-path:XXX}")
    private String tokenPrivateKeyPath;

    @Value("${token.public-key-path:XXX}")
    private String tokenPublicKeyPath;

    /**
     * jdk密钥
     */
    private KeyPair tokenKeyPair;

    public RSAPublicKey getTokenPublicKey() {
        return (RSAPublicKey) getTokenKeyPair().getPublic();
    }

    public RSAPrivateKey getTokenPrivateKey() {
        return (RSAPrivateKey) getTokenKeyPair().getPrivate();
    }

    private KeyPair getTokenKeyPair() {
        if (Objects.isNull(tokenKeyPair)) {
            tokenKeyPair = getKeyPair(tokenPublicKeyPath, tokenPrivateKeyPath);
        }
        return tokenKeyPair;
    }

    /**
     * 获取密钥对
     */
    private KeyPair getKeyPair(String publicKeyPath, String privateKeyPath) {
        Resource publicKeyResource = new ClassPathResource(publicKeyPath);
        Resource privateKeyResource = new ClassPathResource(privateKeyPath);

        if (publicKeyResource.exists() && privateKeyResource.exists()) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");

                byte[] publicKeyBytes = publicKeyResource.getContentAsByteArray();
                EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

                byte[] privateKeyBytes = privateKeyResource.getContentAsByteArray();
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

                return new KeyPair(publicKey, privateKey);
            } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("KeyPair not found");
        }
    }
}