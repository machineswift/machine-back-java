package com.machine.sdk.base.envm.biam.identity;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 授权类型
 */
@Getter
@AllArgsConstructor
public enum BIamAuthorizationGrantTypeEnum implements BaseEnum<BIamAuthorizationGrantTypeEnum, String> {
    CLIENT_CREDENTIALS("CLIENT_CREDENTIALS", "客户端凭证模式"),
    AUTHORIZATION_CODE("AUTHORIZATION_CODE", "授权码模式");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
