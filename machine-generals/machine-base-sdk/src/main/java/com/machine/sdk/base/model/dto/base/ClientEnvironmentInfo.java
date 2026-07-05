package com.machine.sdk.base.model.dto.base;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientEnvironmentInfo {
    /**
     * 客户端IP地址
     */
    private String ipAddress;

    /**
     * 客户端平台（操作系统/设备类型）
     */
    private String platform;

    /**
     * 用户代理（浏览器/客户端标识）
     */
    private String userAgent;
}
