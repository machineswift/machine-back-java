package com.machine.test.temp.ai;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("ai/zhipu")
public class AiZhiPuController {

    @Autowired
    private ZhiPuAiChatModel zhiPuAiChatModel;

    @Resource(type = ZhiPuAiEmbeddingModel.class)
    private EmbeddingModel embeddingModel;

    @GetMapping("test")
    public void test() {
        String content = zhiPuAiChatModel.call("你好，我是谁");
        System.out.println(content);
    }

    @GetMapping("embedding")
    public void embedding() {
        float[] content = embeddingModel.embed("我是谁");
        System.out.println(JSONUtil.toJsonStr(content));
    }

    /**
     * 温度
     */
    @GetMapping("test_temperature")
    public void testTemperature() {
        DeepSeekChatOptions options = DeepSeekChatOptions
                .builder()
                .temperature(1.9d)
                .build();

        ChatResponse response = zhiPuAiChatModel.call(new Prompt("写一首诗描述加班", options));
        System.out.println(response.getResult().getOutput().getText());
    }

    /**
     * 思考模式
     */
    @GetMapping("test_reasoner")
    public void testReasoner() {
        DeepSeekChatOptions options = DeepSeekChatOptions
                .builder()
                .model("GLM-5")
                .build();

        DeepSeekAssistantMessage message = (DeepSeekAssistantMessage) zhiPuAiChatModel
                .call(new Prompt("你好，我是谁", options))
                .getResult().getOutput();

        System.out.println(message.getReasoningContent());
        System.out.println("---------------------------------");
        System.out.println(message.getText());
    }


    @GetMapping("test_stream")
    public void testStream() {
        DeepSeekChatOptions options = DeepSeekChatOptions
                .builder()
                .model("GLM-5")
                .build();

        Flux<ChatResponse> stream = zhiPuAiChatModel.stream(new Prompt("你好，我是谁", options));

        stream.toIterable().forEach(chatResponse -> {
            DeepSeekAssistantMessage message = (DeepSeekAssistantMessage) chatResponse.getResult().getOutput();
            System.out.println(message.getReasoningContent());
        });

        System.out.println("---------------------------------");

        stream.toIterable().forEach(chatResponse -> {
            DeepSeekAssistantMessage message = (DeepSeekAssistantMessage) chatResponse.getResult().getOutput();
            System.out.println(message.getText());
        });
    }

}