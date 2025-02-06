package com.machine.sdk.common.tool;

import cn.hutool.core.codec.Base64;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESUtil {

    private static final String AES = "AES";
    private static final String CHARSET = "UTF-8";

    /**
     * 生成密钥
     */
    @SneakyThrows
    public static String generateSecretKey(String secret) {
        SecureRandom secureRandom = new SecureRandom(secret.getBytes(CHARSET));
        KeyGenerator kg = KeyGenerator.getInstance(AES);
        kg.init(256, secureRandom);
        return Base64.encode(kg.generateKey().getEncoded());
    }

    /**
     * 反向生成SecretKey
     */
    @SneakyThrows
    public static SecretKey reconstructSecretKey(String encodedBase64Key) {
        byte[] decodedKey = Base64.decode(encodedBase64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, AES);
    }

    /**
     * 加密
     */
    @SneakyThrows
    public static String encrypt(String data, SecretKey key) {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes(CHARSET));
        return Base64.encode(encryptedData);
    }

    /**
     * 解密
     */
    @SneakyThrows
    public static String decrypt(String encryptedData, SecretKey key) {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData, CHARSET);
    }
}
