package com.machine.sdk.base.envm.scm.product;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品审核状态
 */
@Getter
@AllArgsConstructor
public enum ScmProductAuditStatusEnum implements BaseEnum<ScmProductAuditStatusEnum, String> {
    /** 无需审核（跳过审批流程） */
    NONE("NONE", "无需审核"),
    /** 待审核 */
    PENDING("PENDING", "待审核"),
    /** 已通过（终态） */
    APPROVED("APPROVED", "已通过"),
    /** 已拒绝（终态） */
    REJECTED("REJECTED", "已拒绝"),
    /** 已取消（终态，流程取消或用户撤回） */
    CANCELLED("CANCELLED", "已取消");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
