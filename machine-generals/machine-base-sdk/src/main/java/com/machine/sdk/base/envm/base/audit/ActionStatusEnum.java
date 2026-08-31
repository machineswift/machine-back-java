package com.machine.sdk.base.envm.base.audit;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActionStatusEnum implements BaseEnum<ActionStatusEnum, String> {

    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
