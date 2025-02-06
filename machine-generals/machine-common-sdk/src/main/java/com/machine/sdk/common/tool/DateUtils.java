package com.machine.sdk.common.tool;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 计算当前日期与给定过期时间戳之间的天数差。
     * 如果过期时间在当前日期之前，则返回-1。
     *
     * @param expirationTimeMillis 过期时间的时间戳（毫秒）
     * @return 天数差，如果过期时间在当前日期之前，则返回-1
     */
    public static int daysUntilExpiration(long expirationTimeMillis) {
        // 将时间戳转换为LocalDate对象（使用系统默认时区）
        LocalDate expirationDate = Instant.ofEpochMilli(expirationTimeMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 计算两个日期之间的天数差
        long daysDifference = ChronoUnit.DAYS.between(currentDate, expirationDate);

        // 如果过期时间在当前日期之前，则返回-1
        return daysDifference >= 0 ? (int) daysDifference : -1;
    }

    /**
     * 返回今天零点的时间
     * @return
     */
    public static Date getTodayBegin() {
        return getDayBegin(Calendar.getInstance());
    }

    /**
     * 获取某天的0点
     * @param calendar
     * @return
     */
    public static Date getDayBegin(Calendar calendar) {
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    /**
     * 将2022-01-14格式的字符串转测时间戳
     */
    public static Long getTimestampByDateString(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(dateString, formatter);
            Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            long timestampMillis = instant.toEpochMilli();
            return timestampMillis;
        } catch (Exception e) {
            return null;
        }
    }
}