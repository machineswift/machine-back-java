package com.machine.sdk.base.envm.scm.product;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品风控状态
 */
@Getter
@AllArgsConstructor
public enum ScmProductRiskStatusEnum implements BaseEnum<ScmProductRiskStatusEnum, String> {
    /** 正常 */
    NORMAL("NORMAL", "正常"),
    /** 冻结（可恢复） */
    FROZEN("FROZEN", "冻结"),
    /** 禁售（通常需审批解禁） */
    BANNED("BANNED", "禁售");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
