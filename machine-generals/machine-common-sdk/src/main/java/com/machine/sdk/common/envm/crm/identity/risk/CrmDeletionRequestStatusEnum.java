package com.machine.sdk.common.envm.crm.identity.risk;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据删除请求状态枚举
 */
@Getter
@AllArgsConstructor
public enum CrmDeletionRequestStatusEnum implements BaseEnum<CrmDeletionRequestStatusEnum, String> {
    PENDING("PENDING", "待处理"),
    PROCESSING("PROCESSING", "处理中"),
    COMPLETED("COMPLETED", "已完成"),
    REJECTED("REJECTED", "已拒绝"),
    CANCELLED("CANCELLED", "已取消");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}