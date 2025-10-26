package com.machine.test.temp.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("ai/chat_client")
public class AiChatClientController {

    @Autowired
    private OllamaChatModel ollamaChatModel;

    @GetMapping("test")
    public void test() {
        ChatClient chatClient = ChatClient
                .builder(ollamaChatModel)
                .build();
        String content = chatClient
                .prompt()
                .user("你好")
                .call()
                .content();
        System.out.println(content);
    }

    @GetMapping("test_stream")
    public void testStream() {
        ChatClient chatClient = ChatClient
                .builder(ollamaChatModel)
                .build();
        Flux<String> content = chatClient
                .prompt()
                .user("你好")
                .stream()
                .content();
        content.toIterable().forEach(System.out::println);
    }

}