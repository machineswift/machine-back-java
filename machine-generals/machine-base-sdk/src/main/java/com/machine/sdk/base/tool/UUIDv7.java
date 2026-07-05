package com.machine.sdk.base.tool;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * UUIDv7 生成工具类
 * 基于 JDK 26 的 UUID.ofEpochMillis() 源码实现
 */
public final class UUIDv7 {

    /**
     * 随机数生成器
     */
    private static final SecureRandom NUMBER_GENERATOR = new SecureRandom();

    /**
     * 私有构造函数，防止实例化
     */
    private UUIDv7() {
        throw new AssertionError("No instances");
    }

    /**
     * 生成 UUIDv7 并返回不带连字符的字符串
     */
    public static String generateWithoutDashes() {
        return generate().toString().replaceAll("-", "");
    }

    /**
     * 使用当前系统时间生成 UUIDv7
     */
    public static UUID generate() {
        return ofEpochMillis(System.currentTimeMillis());
    }

    /**
     * 使用指定时间戳生成 UUIDv7
     */
    public static UUID ofEpochMillis(long timestamp) {
        if ((timestamp >> 48) != 0) {
            throw new IllegalArgumentException("Supplied timestamp: " + timestamp + " does not fit within 48 bits");
        }

        byte[] randomBytes = new byte[16];
        NUMBER_GENERATOR.nextBytes(randomBytes);

        randomBytes[0] = (byte)(timestamp >>> 40);
        randomBytes[1] = (byte)(timestamp >>> 32);
        randomBytes[2] = (byte)(timestamp >>> 24);
        randomBytes[3] = (byte)(timestamp >>> 16);
        randomBytes[4] = (byte)(timestamp >>> 8);
        randomBytes[5] = (byte)(timestamp);

        randomBytes[6] &= 0x0f;
        randomBytes[6] |= 0x70;

        randomBytes[8] &= 0x3f;
        randomBytes[8] |= (byte) 0x80;

        return uuidFromBytes(randomBytes);
    }

    private static UUID uuidFromBytes(byte[] data) {
        if (data.length != 16) {
            throw new IllegalArgumentException("data must be 16 bytes in length");
        }

        long msb = 0;
        long lsb = 0;

        // 构造高 64 位（字节 0-7）
        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (data[i] & 0xff);
        }

        // 构造低 64 位（字节 8-15）
        for (int i = 8; i < 16; i++) {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }

        return new UUID(msb, lsb);
    }

}