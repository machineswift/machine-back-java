package com.machine.starter.obs.constant;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class ObsFileConstant {

    /**
     * 附件默认分组
     */
    public static final String ATTACHMENT_DEFAULT_GROUP  = "DEFAULT";

    /**
     * 路径分隔符
     */
    public static final String SEPARATOR = "/";

    public static String getCurrentDateForFile() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private ObsFileConstant() {
    }
}