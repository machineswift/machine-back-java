package com.machine.test.temp.ai;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.starter.ai.advisor.ReReadingAdvisor;
import com.machine.starter.ai.registry.AiChatClientRegistry;
import com.machine.starter.ai.tool.AiIamUserTools;
import com.machine.test.temp.ai.tool.DateTimeTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

import static com.machine.sdk.base.constant.ContextConstant.SYSTEM_USER_ID;

@Slf4j
@RestController
@RequestMapping("ai/chat_client")
public class AiChatClientController {

    @Autowired
    private AiChatClientRegistry chatClientRegistry;

    @Autowired
    private AiIamUserTools aiIamUserTools;

    @GetMapping("test01")
    public void test01() {

        record ActorFilms(String actor, List<String> movies) {
        }

        SimpleLoggerAdvisor customLogger = new SimpleLoggerAdvisor(
                request -> "Custom request: " + Objects.requireNonNull(request).prompt().getUserMessage(),
                response -> "Custom response: " + Objects.requireNonNull(response).getResult(),
                0
        );

        ChatClient chatClient = chatClientRegistry.getClient();
        ActorFilms actorFilms = chatClient.prompt()
                .advisors(customLogger, new ReReadingAdvisor())
                .user("为一位随机演员生成作品年表，中文回答")
                .call()
                .entity(ActorFilms.class);

        System.out.println(JSONUtil.toJsonStr(actorFilms));
    }


    @GetMapping("test_tool")
    public void testTool() {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);

        ChatClient chatClient = chatClientRegistry.getClient();
        String response = chatClient
                .prompt("我是谁，在10分钟后设置一个闹钟?")
                .tools(new DateTimeTools(), aiIamUserTools)
                .call()
                .content();

        System.out.println(response);
    }

    @GetMapping("test_picture")
    public void testPicture() {
        String content = chatClientRegistry
                .getClient()
                .prompt()
                .user(u -> u.text("解释一下从这张图片看到了什么?")
                        .media(MimeTypeUtils.IMAGE_PNG, new ClassPathResource("doc/image/multimodal.jpeg")))
                .call()
                .content();
        System.out.println(content);
    }


    @GetMapping("test_stream")
    public void testStream() {
        Flux<String> content = chatClientRegistry.getClient()
                .prompt()
                .user("你好")
                .stream()
                .content();
        content.toIterable().forEach(System.out::println);
    }

}