package com.machine.app.mq.consumer;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.message.IDataAppMessageClient;
import com.machine.client.data.message.dto.input.AppMessageSendInputDto;
import com.machine.sdk.common.context.AppContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.ConsumerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;


@Slf4j
@Component
public class BizMessageConsumer {

    @Autowired
    private IDataAppMessageClient messageClient;

    @Bean
    @Profile("!LOCAL")
    public Consumer<Message<AppMessageSendInputDto>> businessMessageConsumer() {
        return ConsumerWrapper.of(msgData -> {
            String userId = (String) msgData.getHeaders().get(USER_ID_KEY);
            AppContext.getContext().setUserId(userId);

            log.info("businessMessageConsumer-接到消息={}", JSONUtil.toJsonStr(msgData.getPayload()));
            messageClient.sendMessage(msgData.getPayload());
        });
    }

}
