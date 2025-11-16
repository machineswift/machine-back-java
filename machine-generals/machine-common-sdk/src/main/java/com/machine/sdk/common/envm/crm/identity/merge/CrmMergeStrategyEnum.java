package com.machine.sdk.common.envm.crm.identity.merge;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份合并策略枚举
 */
@Getter
@AllArgsConstructor
public enum CrmMergeStrategyEnum implements BaseEnum<CrmMergeStrategyEnum, String> {
    AUTO_BY_PHONE("AUTO_BY_PHONE", "手机自动合并"),
    AUTO_BY_WECHAT("AUTO_BY_WECHAT", "微信自动合并"),
    MANUAL("MANUAL", "手动合并"),
    AI_RECOMMEND("AI_RECOMMEND", "AI推荐合并"),
    EXACT_MATCH("EXACT_MATCH", "精确匹配合并");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}