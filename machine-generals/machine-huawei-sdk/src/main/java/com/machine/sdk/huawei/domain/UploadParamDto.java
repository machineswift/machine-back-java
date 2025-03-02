package com.machine.sdk.huawei.domain;

import lombok.Data;

import java.io.InputStream;

/**
 * 参考文档:
 * <a href="https://support.huaweicloud.com/obs/index.html"></a>
 */
@Data
public class UploadParamDto {

    private InputStream inputStream;

    /**
     * 文件路径
     * 1.保证唯一
     * 2.按照规范（注意：不知道拉会讨论，不要随便设置）
     */
    private String filePath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 过期时间（单位:天）
     */
    private int expirationDays = -1;

}
