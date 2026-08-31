package com.machine.sdk.base.envm.biam.auth;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证结果
 */
@Getter
@AllArgsConstructor
public enum BIamAuthResultEnum implements BaseEnum<BIamAuthResultEnum, String> {
    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),;

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
