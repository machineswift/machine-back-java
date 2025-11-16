package com.machine.sdk.common.envm.crm.identity.auth;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 弱身份标识类型
 */
@Getter
@AllArgsConstructor
public enum CrmWeakIdentityTypeEnum implements BaseEnum<CrmWeakIdentityTypeEnum, String> {

    DEVICE_ID("DEVICE_ID", "设备ID"),
    COOKIE("COOKIE", "浏览器Cookie"),
    IP_SEGMENT("IP_SEGMENT", "IP段"),
    BROWSER_FINGERPRINT("BROWSER_FINGERPRINT", "浏览器指纹"),
    MAC_ADDRESS("MAC_ADDRESS", "MAC地址"),
    IMEI("IMEI", "设备IMEI");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}