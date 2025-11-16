package com.machine.sdk.common.envm.crm.identity.auth;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证方法枚举
 */
@Getter
@AllArgsConstructor
public enum CrmVerificationMethodEnum implements BaseEnum<CrmVerificationMethodEnum, String> {
    PHONE_SMS("PHONE_SMS", "手机短信验证"),
    ID_CARD_VERIFY("ID_CARD_VERIFY", "身份证验证"),
    BANK_CARD_VERIFY("BANK_CARD_VERIFY", "银行卡验证"),
    FACE_RECOGNITION("FACE_RECOGNITION", "人脸识别"),
    FINGERPRINT("FINGERPRINT", "指纹验证");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}