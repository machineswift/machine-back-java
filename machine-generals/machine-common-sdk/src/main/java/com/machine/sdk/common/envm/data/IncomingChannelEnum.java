package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 进线渠道
 */
@Getter
@AllArgsConstructor
public enum IncomingChannelEnum implements BaseEnum<IncomingChannelEnum, String> {
    ;

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
