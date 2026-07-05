package com.machine.test.temp.temp;

import java.security.SecureRandom;
import java.util.Base64;

public class Temp {
    public static void main(String[] args) {
        // 生成 256 字节随机数据
        byte[] key = new byte[256];
        new SecureRandom().nextBytes(key);
        // Base64 编码
        String secret = Base64.getEncoder().encodeToString(key);
        System.out.println("Secret (length: " + secret.length() + "):");
        System.out.println(secret);
    }
}
