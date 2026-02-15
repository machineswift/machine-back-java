package com.machine.starter.obs.tool;

public class AttachmentExpireTimeUtil {

    /**
     * 下载中心文件过期时间
     */
    public static long dataDownload() {
        long expireTime = 7 * 24 * 60 * 60 * 1000L;
        return System.currentTimeMillis() + expireTime;
    }


    /**
     * 永久保存时间
     */
    public static long permanent() {
        return Long.MAX_VALUE;
    }
}