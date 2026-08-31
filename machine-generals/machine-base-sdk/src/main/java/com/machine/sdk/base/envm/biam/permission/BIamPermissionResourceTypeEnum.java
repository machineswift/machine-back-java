package com.machine.sdk.base.envm.biam.permission;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BIamPermissionResourceTypeEnum implements BaseEnum<BIamPermissionResourceTypeEnum, String> {
    APP("APP", "应用"),
    MODULE("MODULE", "模块"),
    DIRECTORY("DIRECTORY", "目录"),
    MENU("MENU", "菜单"),
    BUTTON("BUTTON", "按钮");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
