package com.machine.test.temp.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;

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

    @GetMapping("test_stream")
    public void testStream() {
        Flux<String> stream = ollamaChatModel.stream("写一个关于春天的短诗");

        // 订阅并打印每个数据块
        stream.subscribe(
                chunk -> System.out.print(chunk),  // 逐块输出，不换行
                error -> System.err.println("Error: " + error),
                () -> System.out.println("\n\n=== 流式输出完成 ===")
        );
    }

    @GetMapping(value = "test_stream_web", produces = "text/plain;charset=UTF-8")
    public Flux<String> testStreamWeb() {
        String prompt = "讲一个笑话";

        OllamaChatOptions options = OllamaChatOptions.builder()
                .temperature(0.1d)
                .build();

        return ollamaChatModel.stream(new Prompt(prompt, options))
                .map(chatResponse -> {
                    if (chatResponse.getResult() != null) {
                        chatResponse.getResult();
                        return Objects.requireNonNull(chatResponse.getResult().getOutput().getText());
                    }
                    return "";
                })
                .doOnNext(chunk -> {
                    // 每个数据块到达时的回调（例如：记录日志）
                    log.debug("发送数据块: {}", chunk);
                })
                .doOnComplete(() -> {
                    // 流式输出完成时的回调
                    log.info("流式输出完成");
                })
                .doOnError(error -> {
                    // 发生错误时的回调
                    log.error("流式输出出错: {}", error.getMessage());
                });
    }


    @GetMapping("test_multimodality")
    public void testMultimodality() {
        ClassPathResource imageResource = new ClassPathResource("doc/image/地球.jpeg");

        OllamaChatOptions options = OllamaChatOptions
                .builder()
                .model("gemma3:4b")
                .build();

        Media media = new Media(MimeTypeUtils.IMAGE_JPEG, imageResource);

        ChatResponse response = ollamaChatModel.call(
                new Prompt(
                        UserMessage.builder().media(media)
                                .text("识别图片")
                                .build()
                        , options
                )
        );

        System.out.println(response.getResult().getOutput().getText());
    }
}