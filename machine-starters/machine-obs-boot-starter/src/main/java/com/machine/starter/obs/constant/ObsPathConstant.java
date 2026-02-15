package com.machine.starter.obs.constant;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class ObsPathConstant {

    /**
     * 通用下载文件根目录
     */
    public static final String ROOT_DOWNLOADS = "downloads";
    public static final String ROOT_MATERIAL = "material";
    public static final String ROOT_ATTACHMENT = "attachment";


    /**
     * 路径分隔符
     */
    public static final String SEPARATOR = "/";

    public static String getCurrentDateForFile() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private ObsPathConstant() {
    }
}