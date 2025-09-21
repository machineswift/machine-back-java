package com.machine.sdk.common.envm.system;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemTableNameEnum implements BaseEnum<SystemTableNameEnum, String> {

    T_DATA_BRAND("T_DATA_BRAND", "品牌", "品牌"),
    T_DATA_DOWNLOAD("T_DATA_DOWNLOAD", "下载中心", "下载中心"),
    ;

    private final String code;
    private final String message;
    private final String description;

    @Override
    public String getName() {
        return this.name();
    }
}