package com.machine.sdk.base.envm.ai;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AiProviderEnum implements BaseEnum<AiProviderEnum, String> {
    DEEPSEEK("DEEPSEEK", "深度求索"),
    BAI_LIAN("BAI_LIAN", "阿里云百炼"),
    ZHI_PU("ZHI_PU", "智谱"),
    OPENAI("OPENAI", "OpenAI"),
    GOOGLE("GOOGLE", "Google"),
    HUAWEI("HUAWEI", "华为"),
    OLLAMA("OLLAMA", "Ollama");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
