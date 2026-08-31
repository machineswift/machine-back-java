package com.machine.sdk.base.envm.biam.permission;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BIamDataPermissionResultTypeEnum implements BaseEnum<BIamDataPermissionResultTypeEnum, String> {
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
