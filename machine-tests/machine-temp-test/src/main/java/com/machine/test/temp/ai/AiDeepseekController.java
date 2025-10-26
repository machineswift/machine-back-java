package com.machine.test.temp.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("ai/deepseek")
public class AiDeepseekController {

    @Autowired
    private DeepSeekChatModel deepSeekChatModel;

    @GetMapping("test")
    public void test() {
        String content = deepSeekChatModel.call("你好，我是谁");
        System.out.println(content);
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

        ChatResponse response = deepSeekChatModel.call(new Prompt("写一首诗描述加班", options));
        System.out.println(response.getResult().getOutput().getText());
    }

    /**
     * 思考模式
     */
    @GetMapping("test_reasoner")
    public void testReasoner() {
        DeepSeekChatOptions options = DeepSeekChatOptions
                .builder()
                .model("deepseek-reasoner")
                .build();

        DeepSeekAssistantMessage message = (DeepSeekAssistantMessage) deepSeekChatModel
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
                .model("deepseek-reasoner")
                .build();

        Flux<ChatResponse> stream = deepSeekChatModel.stream(new Prompt("你好，我是谁", options));

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