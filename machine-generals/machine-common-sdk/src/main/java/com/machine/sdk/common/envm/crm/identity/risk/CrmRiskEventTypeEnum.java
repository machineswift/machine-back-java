package com.machine.sdk.common.envm.crm.identity.risk;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 风险事件类型枚举
 */
@Getter
@AllArgsConstructor
public enum CrmRiskEventTypeEnum implements BaseEnum<CrmRiskEventTypeEnum, String> {
    SUSPICIOUS_LOGIN("SUSPICIOUS_LOGIN", "可疑登录"),
    IDENTITY_CONFLICT("IDENTITY_CONFLICT", "身份冲突"),
    FRAUD_ATTEMPT("FRAUD_ATTEMPT", "欺诈尝试"),
    DATA_TAMPERING("DATA_TAMPERING", "数据篡改"),
    MULTI_ACCOUNT("MULTI_ACCOUNT", "多账号异常"),
    ABNORMAL_BEHAVIOR("ABNORMAL_BEHAVIOR", "行为异常");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
