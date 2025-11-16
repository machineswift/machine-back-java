package com.machine.sdk.common.envm.crm.identity.risk;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 隐私同意类型枚举
 */
@Getter
@AllArgsConstructor
public enum CrmConsentTypeEnum implements BaseEnum<CrmConsentTypeEnum, String> {
    PRIVACY_POLICY("PRIVACY_POLICY", "隐私政策"),
    MARKETING("MARKETING", "营销授权"),
    DATA_SHARING("DATA_SHARING", "数据共享"),
    LOCATION("LOCATION", "位置信息"),
    NOTIFICATION("NOTIFICATION", "消息通知");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}