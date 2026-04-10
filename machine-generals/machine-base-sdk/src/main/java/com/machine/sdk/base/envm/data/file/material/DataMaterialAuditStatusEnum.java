package com.machine.sdk.base.envm.data.file.material;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核状态
 */
@Getter
@AllArgsConstructor
public enum DataMaterialAuditStatusEnum implements BaseEnum<DataMaterialAuditStatusEnum, String> {
    /** 未提交审核 */
    NOT_SUBMITTED("NOT_SUBMITTED", "未提交审核"),
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
