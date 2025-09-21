package com.machine.starter.mq.function;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.auth.IIamOauth2RegisteredClientClient;
import com.machine.client.iam.auth.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.self.domain.WebHookEventRequestBody;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.starter.mq.constant.MqConstant;
import com.machine.starter.redis.cache.LocalCacheRegisteredClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.UUID;

import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;

@Slf4j
public class CustomerStreamBridge {

    private final StreamBridge streamBridge;

    private final LocalCacheRegisteredClient localCacheRegisteredClient;

    private final IIamOauth2RegisteredClientClient oauth2RegisteredClientClient;


    public CustomerStreamBridge(StreamBridge streamBridge,
                                LocalCacheRegisteredClient localCacheRegisteredClient,
                                IIamOauth2RegisteredClientClient oauth2RegisteredClientClient) {
        this.streamBridge = streamBridge;
        this.localCacheRegisteredClient = localCacheRegisteredClient;
        this.oauth2RegisteredClientClient = oauth2RegisteredClientClient;
    }

    public <T> void send(String producerName,
                         T payload) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    sendPrivate(producerName, payload);
                }
            });
        } else {
            sendPrivate(producerName, payload);
        }
    }

    public <T> void sendWebHookEvent(EventTypeEnum eventType,
                                     T data) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    sendWebHookEventPrivate(eventType, data);
                }
            });
        } else {
            sendWebHookEventPrivate(eventType, data);
        }
    }

    public <T> void sendWebHookEvent(String clientId,
                                     EventTypeEnum eventType,
                                     T data) {
        IamOAuth2RegisteredClientDetailOutputDto registeredClient =
                localCacheRegisteredClient.getByClientId(clientId, oauth2RegisteredClientClient);
        if (registeredClient == null) {
            return;
        }

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    sendWebHookEventPrivate(registeredClient, eventType, data);
                }
            });
        } else {
            sendWebHookEventPrivate(registeredClient, eventType, data);
        }
    }



    private <T> void sendPrivate(String producerName,
                                 T payload) {
        log.info("发送消息到MQ，producerName={} payload={}", producerName, JSONUtil.toJsonStr(payload));
        streamBridge.send(producerName,
                MessageBuilder.withPayload(payload)
                        .setHeader(USER_ID_KEY, AppContext.getContext().getUserId())
                        .setHeader(MqConstant.HEAD_KEY_PRODUCER_NAME, producerName)
                        .setHeader(MqConstant.HEAD_KEY_MESSAGE_UUID, UUID.randomUUID().toString().replace("-", ""))
                        .build());
    }

    private <T> void sendWebHookEventPrivate(EventTypeEnum eventType,
                                             T data) {
        List<String> clientIdList = localCacheRegisteredClient.allRegisteredClientIds(oauth2RegisteredClientClient);
        for (String clientId : clientIdList) {
            IamOAuth2RegisteredClientDetailOutputDto registeredClient =
                    localCacheRegisteredClient.getByClientId(clientId, oauth2RegisteredClientClient);
            if (registeredClient == null) {
                continue;
            }
            sendWebHookEventPrivate(registeredClient, eventType, data);
        }
    }

    private <T> void sendWebHookEventPrivate(IamOAuth2RegisteredClientDetailOutputDto registeredClient,
                                             EventTypeEnum eventType,
                                             T data) {
        String producerName = "WebHookFastEventProducer";
        String messageId = UUID.randomUUID().toString().replace("-", "");
        WebHookEventRequestBody<T> messageBody = new WebHookEventRequestBody<>(eventType, messageId, data);

        //应用Id
        String clientId = registeredClient.getClientId();
        messageBody.setClientId(clientId);
        if (StatusEnum.DISABLE == registeredClient.getStatus()) {
            log.info("WebHookFastEventProducer-应用禁用忽略消息，clientId={} requestBody={}",
                    clientId, JSONUtil.toJsonStr(messageBody));
            return;
        }

        if (null == registeredClient.getWebHookInfo()) {
            log.warn("WebHookFastEventProducer-应用未配置WebHook信息忽略消息，clientId={} requestBody={}",
                    clientId, JSONUtil.toJsonStr(messageBody));
            return;
        }

        //开始发送消息
        log.info("WebHookFastEventProducer-事件消息发送，producerName={} clientId={} messageBody={}",
                producerName, clientId, JSONUtil.toJsonStr(messageBody));
        streamBridge.send(producerName,
                MessageBuilder.withPayload(messageBody)
                        .setHeader(USER_ID_KEY, AppContext.getContext().getUserId())
                        .setHeader(MqConstant.HEAD_KEY_PRODUCER_NAME, producerName)
                        .setHeader(MqConstant.HEAD_KEY_MESSAGE_UUID, UUID.randomUUID().toString().replace("-", ""))
                        .build());
    }

}
