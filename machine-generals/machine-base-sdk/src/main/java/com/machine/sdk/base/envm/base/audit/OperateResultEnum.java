package com.machine.sdk.base.envm.base.audit;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperateResultEnum implements BaseEnum<OperateResultEnum, String> {

    SUCCESS("SUCCESS", "成功"),
    FAILED("FAILED", "失败");
    
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }

}
