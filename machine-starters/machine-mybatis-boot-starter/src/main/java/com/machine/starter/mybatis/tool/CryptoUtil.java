package com.machine.starter.mybatis.tool;

import com.machine.starter.mybatis.MybatisProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES 加密工具类
 */
@Slf4j
@Component
public class CryptoUtil {

    private static MybatisProperties properties;

    @Autowired
    public void setAiProperties(MybatisProperties properties) {
        CryptoUtil.properties = properties;
    }

    /**
     * 获取密钥
     */
    private static String getSecretKey() {
        String secret = properties.getSecret();
        int len = secret.length();
        if (len != 32) {
            throw new IllegalStateException("加密密钥的长度必须是32, 现在: " + len);
        }
        return secret;
    }


    /**
     * 获取 Cipher 实例
     */
    private static Cipher getCipher(int mode) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(getSecretKey()
                .getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(mode, keySpec);
        return cipher;
    }

    /**
     * 加密
     */
    @SneakyThrows
    public static String encrypt(String plainText) {
        if (plainText == null) {
            return null;
        }
        if (plainText.isEmpty()) {
            return "";
        }
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 解密
     */
    @SneakyThrows
    public static String decrypt(String cipherText) {
        if (cipherText == null) {
            return null;
        }
        if (cipherText.isEmpty()) {
            return "";
        }
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
        byte[] decoded = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

}