package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataPermissionResultTypeEnum implements BaseEnum<DataPermissionResultTypeEnum, String> {
    ALL("ALL", "全部"),
    PART("PART", "部分"),
    NONE("NONE", "无");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
