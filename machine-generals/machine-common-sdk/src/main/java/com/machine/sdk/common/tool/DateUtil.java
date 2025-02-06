package com.machine.sdk.common.tool;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * packageName com.machine.sdk.common.tool
 *
 * @author chenjie
 * @version JDK 8
 * @className DateUtil (此处以class为例)
 * @date 2024/12/3
 * @description TODO
 */
public class DateUtil {

     /**
     * 将时间戳转换为 LocalDateTime
     *
     * @param timestamp 时间戳
     * @return LocalDateTime 对象
     */
    public static LocalDateTime convertTimestampToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
