package com.machine.sdk.common.envm.data.attachment;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataAttachmentStatusEnum implements BaseEnum<DataAttachmentStatusEnum, String> {
    DRAFT("DRAFT", "草稿", "已创建但未提交"),
    PENDING_REVIEW("PENDING_REVIEW", "待审核", "已提交等待审核"),
    APPROVED("APPROVED", "已审核", "审核通过可用"),
    REJECTED("REJECTED", "已拒绝", "审核未通过"),
    PUBLISHED("PUBLISHED", "已发布", "已正式发布使用"),
    DISABLED("DISABLED", "已禁用", "管理员手动禁用"),
    ARCHIVED("ARCHIVED", "已归档", "已归档不再使用");

    private final String code;
    private final String message;
    private final String description;

    @Override
    public String getName() {
        return this.name();
    }
}