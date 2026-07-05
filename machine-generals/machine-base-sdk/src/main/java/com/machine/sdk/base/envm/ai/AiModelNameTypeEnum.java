package com.machine.sdk.base.envm.ai;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AiModelNameTypeEnum implements BaseEnum<AiModelNameTypeEnum, String> {
    DEEPSEEK_FLASH("DEEPSEEK_FLASH", "deepseek_flash"),
    DEEPSEEK_PRO("DEEPSEEK_PRO", "deepseek_pro"),
    OLLAMA_DEFAULT("OLLAMA_DEFAULT", "ollama_default"),
    OLLAMA_DEEPSEEK_R1("OLLAMA_DEEPSEEK_R1", "ollama_deepseek_r1"),
    OLLAMA_QWEN3("OLLAMA_QWEN3", "ollama_qwen3"),
    OLLAMA_QWEN3VL("OLLAMA_QWEN3VL", "ollama_qwen3-vl");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
