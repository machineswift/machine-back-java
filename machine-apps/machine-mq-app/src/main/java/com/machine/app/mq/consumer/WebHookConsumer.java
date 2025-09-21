package com.machine.app.mq.consumer;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.auth.IIamOauth2RegisteredClientClient;
import com.machine.client.iam.auth.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.dto.data.WebHookInfoDto;
import com.machine.sdk.self.client.WebhookClient;
import com.machine.sdk.self.domain.WebHookEventRequestBody;
import com.machine.starter.redis.cache.LocalCacheRegisteredClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
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
public class WebHookConsumer {

    @Autowired
    private LocalCacheRegisteredClient localCacheRegisteredClient;

    @Autowired
    private IIamOauth2RegisteredClientClient oauth2RegisteredClientClient;

    @Resource(name = "selfWebHookSdkHttpClient")
    private OkHttpClient okHttpClient;

    @Bean
    @Profile("!LOCAL")
    public <T> Consumer<Message<WebHookEventRequestBody<T>>> webHookFastEventConsumer() {
        return ConsumerWrapper.of(msgData -> {
            String userId = (String) msgData.getHeaders().get(USER_ID_KEY);
            AppContext.getContext().setUserId(userId);

            log.info("WebHookFastEventConsumer-接到消息={}", JSONUtil.toJsonStr(msgData.getPayload()));
            processWebHookCallback(msgData.getPayload());
        });
    }

    private <T> void processWebHookCallback(WebHookEventRequestBody<T> requestBody) {
        String clientId = requestBody.getClientId();
        IamOAuth2RegisteredClientDetailOutputDto outputDto = localCacheRegisteredClient
                .getByClientId(clientId, oauth2RegisteredClientClient);

        if (StatusEnum.DISABLE == outputDto.getStatus()) {
            log.info("WebHookFastEventConsumer-应用禁用忽略消息， requestBody={}", JSONUtil.toJsonStr(requestBody));
            return;
        }

        WebHookInfoDto webHookInfo = outputDto.getWebHookInfo();
        if (null == webHookInfo ||
                StrUtil.isBlank(webHookInfo.getCallBackUrl()) ||
                StrUtil.isBlank(webHookInfo.getSecret())) {
            log.warn("WebHookFastEventConsumer-应用未配置回调地址忽略消息， requestBody={}", JSONUtil.toJsonStr(requestBody));
            return;
        }

        WebhookClient.callBack(okHttpClient, webHookInfo, requestBody);
    }

}
