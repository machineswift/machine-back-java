package com.machine.starter.mq.annotation;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.message.IDataMqReliableMessageClient;
import com.machine.client.data.message.dto.input.MqReliableMessageCreateInputDto;
import com.machine.client.data.message.dto.input.MqReliableMessageUpdate4ExceptionInputDto;
import com.machine.client.data.message.dto.output.MqReliableMessageDetailDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.starter.mq.constant.MqConstant;
import com.machine.starter.mq.domain.MqReliableMessageDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Aspect
@Component
public class MqReliableMessageAspect {

    @Autowired
    private IDataMqReliableMessageClient mqMessageClient;

    /**
     * 定义一个切入点
     */
    @Pointcut("@annotation(com.machine.starter.mq.annotation.MqReliableMessageAnnotation)")
    public void producer() {
        log.debug("Pointcut matched");
    }

    @Before("producer()")
    public void before() {
        AppContext.getContext().clear();
    }


    /**
     * 环绕通知
     */
    @Around(value = "producer()&&@annotation(reliableAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint,
                         MqReliableMessageAnnotation reliableAnnotation) throws Throwable {
        log.debug("进入环绕通知，方法名=========================");
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        Message<String> msgData = (Message<String>) args[0];

        MessageHeaders headers = msgData.getHeaders();
        if (!headers.containsKey(MqConstant.HEAD_KEY_RELIABLE_MESSAGE)) {
            //第一次消费，不是重试消息忽略
            return joinPoint.proceed();
        }

        String message = msgData.getPayload();
        MqReliableMessageDto reliableMessage = JSONUtil.toBean(
                (String) headers.get(MqConstant.HEAD_KEY_RELIABLE_MESSAGE), MqReliableMessageDto.class);

        if (!reliableAnnotation.consumerName().equals(reliableMessage.getConsumerName())) {
            //重试消息广播场景：非自己消费的消息则跳过,防止重复处理
            log.warn("消费消息忽略非自己消费的可靠消息，consumerName={}-{} reliableMessage={} message={}",
                    reliableAnnotation.consumerName(), reliableMessage.getConsumerName(),
                    JSONUtil.toJsonStr(reliableMessage), message);
            return null;
        }

        //判断可靠消息有没有删除，删除了则忽略
        Boolean contain = mqMessageClient.contain(reliableMessage.getId());
        if (!contain) {
            //重试消息已经被其他线程处理好了，不需要重复处理
            log.warn("重试消息已经北其他线程处理好了，不需要重复处理，consumerName={}-{} reliableMessage={} message={}",
                    reliableAnnotation.consumerName(), reliableMessage.getConsumerName(),
                    JSONUtil.toJsonStr(reliableMessage), message);
            return null;
        }
        return joinPoint.proceed();
    }

    /**
     * 后置通知(方法返回)
     */
    @AfterReturning(value = "producer()&&@annotation(reliableAnnotation)")
    public void afterReturning(ProceedingJoinPoint joinPoint,
                               MqReliableMessageAnnotation reliableAnnotation) {
        log.debug("后置通知，方法名=========================");

        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        Message<String> msgData = (Message<String>) args[0];

        MessageHeaders headers = msgData.getHeaders();
        if (!headers.containsKey(MqConstant.HEAD_KEY_RELIABLE_MESSAGE)) {
            //第一次消费，不是重试消息忽略
            return;
        }

        MqReliableMessageDto reliableMessage = JSONUtil.toBean(
                (String) headers.get(MqConstant.HEAD_KEY_RELIABLE_MESSAGE), MqReliableMessageDto.class);

        if (!reliableAnnotation.consumerName().equals(reliableMessage.getConsumerName())) {
            //重试消息广播场景：非自己消费的消息则跳过,防止重复处理
            return;
        }

        mqMessageClient.delete(reliableMessage.getId());
    }

    /**
     * 异常通知
     */
    @AfterThrowing(value = "producer()&&@annotation(reliableAnnotation)", throwing = "exception")
    public void afterThrowing(ProceedingJoinPoint joinPoint,
                              MqReliableMessageAnnotation reliableAnnotation,
                              Exception exception) {
        log.debug("异常通知，方法名=========================");

        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        Message<String> msgData = (Message<String>) args[0];

        log.error(String.format("消费消息异常,%s msgData:%s ",
                reliableAnnotation.consumerName(), JSONUtil.toJsonStr(msgData)), exception);

        MessageHeaders headers = msgData.getHeaders();
        //补偿逻辑处理
        int[] retryStrategyArr = reliableAnnotation.retryStrategy();
        if (retryStrategyArr.length == 0) {
            return;
        }

        if (!headers.containsKey(MqConstant.HEAD_KEY_RELIABLE_MESSAGE)) {
            String message = msgData.getPayload();
            Long currentTimeMillis = System.currentTimeMillis();

            //第一次重试,添加消费者以及补偿策略信息
            MqReliableMessageDto reliableMessage = new MqReliableMessageDto();
            reliableMessage.setMessageUuid((String) headers.get(MqConstant.HEAD_KEY_MESSAGE_UUID));
            reliableMessage.setProducerName((String) headers.get(MqConstant.HEAD_KEY_PRODUCER_NAME));
            reliableMessage.setConsumerName(reliableAnnotation.consumerName());
            reliableMessage.setResendTimes(0);
            reliableMessage.setLastSendTime(currentTimeMillis);
            reliableMessage.setConsumerTimes(1);
            reliableMessage.setLastConsumerTime(currentTimeMillis);
            reliableMessage.setNextExeTime(currentTimeMillis + retryStrategyArr[0] * 1000L);
            reliableMessage.setRetryStrategy(JSONUtil.toJsonStr(retryStrategyArr));
            reliableMessage.setContent(message);
            reliableMessage.setFailReason(convertException2String(exception));
            mqMessageClient.create(JSONUtil.toBean(JSONUtil.toJsonStr(reliableMessage), MqReliableMessageCreateInputDto.class));
        } else {

            MqReliableMessageDto reliableMessage = JSONUtil.toBean(
                    (String) headers.get(MqConstant.HEAD_KEY_RELIABLE_MESSAGE), MqReliableMessageDto.class);

            if (!reliableAnnotation.consumerName().equals(reliableMessage.getConsumerName())) {
                //重试消息广播场景：非自己消费的消息则跳过,防止重复处理
                return;
            }

            //重新查询,防止消息消息延迟引起的重试策略错乱
            MqReliableMessageDetailDto reliableMessageDetailDto = mqMessageClient.getById(reliableMessage.getId());
            if (null == reliableMessageDetailDto) {
                //已经被正确处理或已经被移到dead里面
                return;
            }

            MqReliableMessageUpdate4ExceptionInputDto update4ExceptionInputDto = new MqReliableMessageUpdate4ExceptionInputDto();
            update4ExceptionInputDto.setId(reliableMessageDetailDto.getMessageUuid());
            update4ExceptionInputDto.setFailReason(convertException2String(exception));

            //发送次数超过消费次数（防止mq队列阻塞引起可靠消息被标记为死亡，预计时间和保险时间取较大的）
            long nextUnixTimestamp;
            if (reliableMessageDetailDto.getResendTimes() >= reliableMessageDetailDto.getConsumerTimes()) {
                //保险时间
                long timeMillis = reliableMessage.getNextExeTime() - reliableMessage.getLastConsumerTime();
                nextUnixTimestamp = (reliableMessage.getResendTimes() - reliableMessage.getConsumerTimes() + 1) * timeMillis;

                //预计时间
                String retryStrategy = reliableMessage.getRetryStrategy();
                List<Integer> retryList = JSONUtil.toList(retryStrategy, Integer.class);
                if (reliableMessage.getResendTimes() < retryList.size()) {
                    long nextExpectationUnixTimestamp = retryList.get(reliableMessage.getResendTimes()) * 1000L;
                    if (nextExpectationUnixTimestamp > nextUnixTimestamp) {
                        nextUnixTimestamp = nextExpectationUnixTimestamp;
                    }
                }
            } else {
                String retryStrategy = reliableMessage.getRetryStrategy();
                List<Integer> retryList = JSONUtil.toList(retryStrategy, Integer.class);
                nextUnixTimestamp = retryList.get(reliableMessage.getResendTimes()) * 1000L;
            }

            update4ExceptionInputDto.setNextUnixTimestamp(nextUnixTimestamp);
            mqMessageClient.update4Exception(update4ExceptionInputDto);
        }
    }

    @After("producer()")
    public void after() {
        AppContext.getContext().clear();
    }


    private String convertException2String(Exception exception) {
        StringBuilder reason = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        reason.append(exception.toString())
                .append(": ")
                .append(exception.getMessage())
                .append(lineSeparator);
        for (StackTraceElement elem : exception.getStackTrace()) {
            reason.append(elem)
                    .append(lineSeparator);
        }
        return reason.toString();
    }

}
