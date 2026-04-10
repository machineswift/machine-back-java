package com.machine.sdk.base.envm.data;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataShopEmployeeStatusEnum implements BaseEnum<DataShopEmployeeStatusEnum, String> {
    FULL_TIME("FULL_TIME", "正式"),
    LEFT("LEFT", "离职");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
