package com.machine.sdk.base.envm.biam.user;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户偏好配置键
 */
@Getter
@AllArgsConstructor
public enum BIamUserConfigKeyEnum implements BaseEnum<BIamUserConfigKeyEnum, String> {
    DOCK_CONFIG("DOCK_CONFIG", "程序坞配置");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
