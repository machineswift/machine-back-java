package com.machine.sdk.base.envm.data.filecenter.attachment;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataAttachmentStatusEnum implements BaseEnum<DataAttachmentStatusEnum, String> {
    ENABLED("ENABLED", "正常"),
    DISABLED("DISABLED", "禁用"),
    EXPIRED("EXPIRED", "过期"),
    LOCKED("LOCKED", "锁定"),
    ARCHIVED("ARCHIVED", "归档"),
    DELETED("DELETED", "已删除");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }


    // 状态流转校验
    public boolean canTransitionTo(DataAttachmentStatusEnum target) {
        return switch (this) {
            case ENABLED -> target == LOCKED || target == EXPIRED || target == DISABLED || target == ARCHIVED;
            case LOCKED -> target == ENABLED || target == DISABLED || target == ARCHIVED;
            case EXPIRED -> target == DELETED || target == DISABLED || target == ARCHIVED;
            case DISABLED -> target == ENABLED || target == DELETED || target == ARCHIVED;
            case ARCHIVED -> target == DELETED;
            case DELETED -> false;
        };
    }
}