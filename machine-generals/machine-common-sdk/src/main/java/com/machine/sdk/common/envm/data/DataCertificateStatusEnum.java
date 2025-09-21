package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataCertificateStatusEnum implements BaseEnum<DataCertificateStatusEnum, String> {
    MISSING("MISSING", "缺失"),
    NORMAL("NORMAL", "正常"),
    NEAR_EXPIRATION("NEAR_EXPIRATION", "临期"),
    EXPIRED("EXPIRED", "过期");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}