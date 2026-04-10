package com.machine.sdk.base.envm.scm.product;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品业务状态
 */
@Getter
@AllArgsConstructor
public enum ScmProductBusinessStatusEnum implements BaseEnum<ScmProductBusinessStatusEnum, String> {
    /** 草稿 */
    DRAFT("DRAFT", "草稿"),
    /** 待生效（如审批通过后等待生效） */
    PENDING_ACTIVE("PENDING_ACTIVE", "待生效"),
    /** 生效中 */
    ACTIVE("ACTIVE", "生效中"),
    /** 已禁用 */
    DISABLED("DISABLED", "已禁用"),
    /** 已归档 */
    ARCHIVED("ARCHIVED", "已归档");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
