package com.machine.sdk.base.envm.ai;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AiModelCapabilityEnum implements BaseEnum<AiModelCapabilityEnum, String> {
    CHAT("CHAT", "对话模型"),

    VISION("VISION", "视觉理解模型"),
    ASR("ASR", "语音识别模型"),

    IMAGE("IMAGE", "图像生成模型"),
    TTS("TTS", "语音合成模型"),
    VIDEO("VIDEO", "视频生成模型"),

    EMBEDDING("EMBEDDING", "向量嵌入模型");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
