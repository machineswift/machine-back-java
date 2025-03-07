package com.machine.sdk.common.envm.iam.auth;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证结果
 */
@Getter
@AllArgsConstructor
public enum AuthResultEnum implements BaseEnum<AuthResultEnum, String> {
    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),;

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
