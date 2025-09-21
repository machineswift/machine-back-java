package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamPermissionResourceTypeEnum implements BaseEnum<IamPermissionResourceTypeEnum, String> {
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
