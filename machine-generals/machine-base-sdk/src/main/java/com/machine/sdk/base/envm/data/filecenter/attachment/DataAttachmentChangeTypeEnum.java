package com.machine.sdk.base.envm.data.filecenter.attachment;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataAttachmentChangeTypeEnum implements BaseEnum<DataAttachmentChangeTypeEnum, String> {

    CREATE("CREATE", "创建", "首次创建版本"),
    UPDATE("UPDATE", "更新", "基于当前版本创建新版本"),
    ROLLBACK("ROLLBACK", "回滚", "回滚到历史版本"),
    DELETE("DELETE", "删除", "删除版本");

    private final String code;
    private final String message;
    private final String description;

    DataAttachmentChangeTypeEnum(String code, String message) {
        this(code, message, message);
    }

    @Override
    public String getName() {
        return this.name();
    }

    public boolean canTransitionTo(DataAttachmentChangeTypeEnum target) {
        if (this == target) {
            return true;
        }

        return switch (this) {
            case CREATE, UPDATE -> target == UPDATE || target == ROLLBACK || target == DELETE;
            case ROLLBACK -> target == UPDATE || target == DELETE;
            case DELETE -> false;
        };
    }

    /**
     * 是否为初始变更
     */
    public boolean isInitial() {
        return this == CREATE;
    }

    /**
     * 是否为有效变更（需要业务确认）
     */
    public boolean requiresConfirmation() {
        return this == CREATE || this == UPDATE || this == ROLLBACK;
    }

}