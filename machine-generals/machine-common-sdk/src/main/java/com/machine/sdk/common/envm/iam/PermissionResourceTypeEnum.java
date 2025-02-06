package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionResourceTypeEnum implements BaseEnum<PermissionResourceTypeEnum, String> {
    APP("APP", "应用"),
    MODULE("MODULE", "模块"),
    MENU("MENU", "菜单"),
    BUTTON("BUTTON", "按钮");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
