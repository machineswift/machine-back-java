package com.machine.sdk.base.envm.ai;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AiModelFeatureEnum implements BaseEnum<AiModelFeatureEnum, String> {
    DEEP_THINKING("DEEP_THINKING", "深度思考"),

    THINKING_MODE("THINKING_MODE", "思考模式"),

    STREAMING("STREAMING", "流式消息"),
    TOOL_STREAMING("TOOL_STREAMING", "工具流式输出"),

    TOOL_CALL("TOOL_CALL", "工具调用"),

    CONTEXT_CACHE("CONTEXT_CACHE", "上下文缓存"),

    STRUCTURED_OUTPUT("STRUCTURED_OUTPUT", "结构化输出");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
