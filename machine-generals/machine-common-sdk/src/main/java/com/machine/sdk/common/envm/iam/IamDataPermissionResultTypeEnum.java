package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamDataPermissionResultTypeEnum implements BaseEnum<IamDataPermissionResultTypeEnum, String> {
    ALL("ALL", "全部"),
    PART("PART", "部分"),
    CUSTOMER("CUSTOMER", "自定义"),
    NONE("NONE", "无");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
