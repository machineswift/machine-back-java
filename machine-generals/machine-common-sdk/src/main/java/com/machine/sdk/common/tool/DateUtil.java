package com.machine.sdk.common.tool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    /**
     * 获取当前日期字符串 (yyyy-MM-dd)
     */
    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

}
