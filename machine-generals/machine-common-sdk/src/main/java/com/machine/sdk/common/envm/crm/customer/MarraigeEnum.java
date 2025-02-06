package com.machine.sdk.common.envm.crm.customer;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 婚姻状况
 */
@Getter
@AllArgsConstructor
public enum MarraigeEnum implements BaseEnum<MarraigeEnum, String> {

    SECRECY("SECRECY", "保密"),
    MARRIED("MARRIED", "已婚"),
    UNMARRIED("UNMARRIED", "未婚");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
