package com.machine.test.temp.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("ai/ollama")
public class AiOllamaController {

    @Autowired
    private OllamaChatModel ollamaChatModel;

    @GetMapping("test")
    public void test() {
        String content = ollamaChatModel.call("你好，我是谁");
        System.out.println(content);
    }

    /**
     * 温度
     */
    @GetMapping("test_temperature")
    public void testTemperature() {
        OllamaChatOptions options = OllamaChatOptions
                .builder()
                .temperature(1.9d)
                .build();

        ChatResponse response = ollamaChatModel.call(new Prompt("写一首诗描述加班", options));
        System.out.println(response.getResult().getOutput().getText());
    }

    /**
     * 思考模式
     */
    @GetMapping("test_reasoner")
    public void testReasoner() {

    }


    @GetMapping("test_stream")
    public void testStream() {

    }

    @GetMapping("test_multimodality")
    public void testMultimodality() {

    }
}