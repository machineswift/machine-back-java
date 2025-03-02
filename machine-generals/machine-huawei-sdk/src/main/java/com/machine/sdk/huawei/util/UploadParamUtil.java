package com.machine.sdk.huawei.util;

import com.machine.sdk.huawei.domain.UploadParamDto;

import java.io.InputStream;
import java.util.UUID;

/**
 * 路径参考：ARCHITECTURE.md（注意：不知道拉会讨论，不要随便设置）
 */
public class UploadParamUtil {

    /**
     * 下载中心
     */
    public static UploadParamDto get4Download(InputStream inputStream,
                                              String fileName,
                                              int expirationDays) {
        UploadParamDto uploadParamDto = new UploadParamDto();
        //文件路径
        String filePath = "/download/" + UUID.randomUUID().toString().replace("-", "") + "/";
        uploadParamDto.setFilePath(filePath);

        uploadParamDto.setFileName(fileName);
        uploadParamDto.setInputStream(inputStream);
        uploadParamDto.setExpirationDays(expirationDays);
        return uploadParamDto;
    }

}
