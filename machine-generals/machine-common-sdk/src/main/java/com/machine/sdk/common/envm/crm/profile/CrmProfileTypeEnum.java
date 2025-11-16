package com.machine.sdk.common.envm.crm.profile;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 档案类型枚举
 */
@Getter
@AllArgsConstructor
public enum CrmProfileTypeEnum implements BaseEnum<CrmProfileTypeEnum, String> {
    BASIC("BASIC", "基础档案"),
    EXTENDED("EXTENDED", "扩展档案"),
    PREFERENCE("PREFERENCE", "偏好档案"),
    BEHAVIOR("BEHAVIOR", "行为档案"),
    FINANCIAL("FINANCIAL", "金融档案");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
