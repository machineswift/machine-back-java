package com.machine.starter.ai.registry;

import com.machine.sdk.base.context.AppAiContextHolder;
import com.machine.sdk.base.envm.ai.AiModelNameTypeEnum;
import com.machine.sdk.base.exception.ai.AiBusinessException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AiChatClientRegistry {

    private final Map<AiModelNameTypeEnum, ChatClient> clientMap = new ConcurrentHashMap<>();

    public AiChatClientRegistry(@Qualifier("deepSeekFlashChatClient") ChatClient deepSeekFlash,
                                @Qualifier("deepSeekProChatClient") ChatClient deepSeekPro,
                                @Qualifier("ollamaDefaultChatClient") ChatClient ollama,
                                @Qualifier("ollamaDeepseekR1ChatClient") ChatClient ollamaDeepseekR1,
                                @Qualifier("ollamaQwen3ChatClient") ChatClient ollamaQwen3,
                                @Qualifier("ollamaQwen3VLChatClient") ChatClient ollamaQwen3VL) {
        clientMap.put(AiModelNameTypeEnum.DEEPSEEK_FLASH, deepSeekFlash);
        clientMap.put(AiModelNameTypeEnum.DEEPSEEK_PRO, deepSeekPro);
        clientMap.put(AiModelNameTypeEnum.OLLAMA_DEFAULT, ollama);
        clientMap.put(AiModelNameTypeEnum.OLLAMA_DEEPSEEK_R1, ollamaDeepseekR1);
        clientMap.put(AiModelNameTypeEnum.OLLAMA_QWEN3, ollamaQwen3);
        clientMap.put(AiModelNameTypeEnum.OLLAMA_QWEN3VL, ollamaQwen3VL);
    }

    public ChatClient getClient() {
        AiModelNameTypeEnum modelType = AppAiContextHolder.getContext().getModelType();
        if (null == modelType) {
            throw new AiBusinessException("ai.boot.starter.registry.nullModelType", "AI模型类型为空");
        }
        return clientMap.get(modelType);
    }
}
