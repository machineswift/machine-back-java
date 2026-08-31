package com.machine.sdk.base.envm.biam.role;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BIamOpenApiDefaultRoleEnum implements BaseEnum<BIamOpenApiDefaultRoleEnum, String> {
    OPENAPI_BASIC_READ("OPENAPI_BASIC_READ", "基础数据只读"),
    OPENAPI_BASIC_SYNC("OPENAPI_BASIC_SYNC", "基础数据同步");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}