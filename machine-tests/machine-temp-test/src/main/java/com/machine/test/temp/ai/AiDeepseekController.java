package com.machine.test.temp.ai;

import com.machine.starter.ai.advisor.ReReadingAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("ai/deepseek")
public class AiDeepseekController {

    @Value("classpath:/doc/prompts/system-message.st")
    private Resource systemResource;

    @Autowired
    private ChatMemory chatMemory;

    @Autowired
    private DeepSeekChatModel deepSeekChatModel;

    @GetMapping("test")
    public void test() {
        String content = deepSeekChatModel
                .call("你好，我是谁");
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

        DeepSeekAssistantMessage message = (DeepSeekAssistantMessage) Objects.requireNonNull(deepSeekChatModel
                .call(new Prompt("你好，我是谁", options))
                .getResult()).getOutput();

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
    }

    @GetMapping("test_system_promt")
    public void testSystemPromt() {

        ChatClient chatClient = ChatClient
                .builder(deepSeekChatModel)
                .defaultSystem(systemResource)
                .build();

        String content = chatClient
                .prompt()
                .system(p -> p.param("name", "陈"))
                .user("你好")
                .call()
                .content();
        System.out.println(content);
    }


    @GetMapping("test_advisor")
    public void testAdvisor() {
        ChatClient chatClient = ChatClient
                .builder(deepSeekChatModel)
                //.defaultAdvisors(new SimpleLoggerAdvisor())
                .build();

        String content = chatClient
                .prompt()
                .user("今天天气怎么样")
                .call()
                .content();
        System.out.println(content);
    }

    @GetMapping("test_sensitive")
    public void testSensitive(@RequestParam("message") String message) {
        ChatClient chatClient = ChatClient
                .builder(deepSeekChatModel)
                //.defaultAdvisors(new SimpleLoggerAdvisor())
                .build();

        String content = chatClient
                .prompt()
                .advisors(new SafeGuardAdvisor(List.of("狗")))
                .user(message)
                .call()
                .content();
        System.out.println(content);
    }

    @GetMapping("test_ReReading")
    public void testReReading(@RequestParam("message") String message) {
        ChatClient chatClient = ChatClient
                .builder(deepSeekChatModel)
                //.defaultAdvisors(new SimpleLoggerAdvisor())
                .build();

        String content = chatClient
                .prompt()
                .advisors(new ReReadingAdvisor())
                .user(message)
                .call()
                .content();
        System.out.println(content);
    }


    @GetMapping("test_memory")
    public void testMemory() {

        ChatClient chatClient = ChatClient
                .builder(deepSeekChatModel)
                //.defaultAdvisors(
               //         new SimpleLoggerAdvisor(),
                //        PromptChatMemoryAdvisor.builder(chatMemory).build()
                //)
                .build();

        String content = chatClient
                .prompt()
                .user("今天星期几")
                .call()
                .content();
        System.out.println(content);

        content = chatClient
                .prompt()
                .user("今天几号")
                .call()
                .content();
        System.out.println(content);
    }


}