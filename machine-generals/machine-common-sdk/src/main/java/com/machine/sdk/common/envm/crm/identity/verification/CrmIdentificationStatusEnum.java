package com.machine.sdk.common.envm.crm.identity.verification;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 识别状态枚举
 */
@Getter
@AllArgsConstructor
public enum CrmIdentificationStatusEnum implements BaseEnum<CrmIdentificationStatusEnum, String> {
    PENDING("PENDING", "待识别"),
    IDENTIFIED("IDENTIFIED", "已识别"),
    AMBIGUOUS("AMBIGUOUS", "模糊匹配"),
    FAILED("FAILED", "识别失败"),
    TIMEOUT("TIMEOUT", "识别超时");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}