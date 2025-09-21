package com.machine.service.data.mesage.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.message.dto.input.MqReliableMessageCreateInputDto;
import com.machine.client.data.message.dto.input.MqReliableMessageUpdate4ExceptionInputDto;
import com.machine.client.data.message.dto.output.MqReliableMessageDetailDto;
import com.machine.service.data.common.dao.IDataCommonDao;
import com.machine.service.data.mesage.dao.IDataMqDeadMessageDao;
import com.machine.service.data.mesage.dao.IDataMqMessageExternalDao;
import com.machine.service.data.mesage.dao.IDataMqReliableMessageDao;
import com.machine.service.data.mesage.dao.mapper.entity.DataMqMessageExternalEntity;
import com.machine.service.data.mesage.dao.mapper.entity.DataMqReliableMessageEntity;
import com.machine.service.data.mesage.service.IDataMqReliableMessageService;
import com.machine.starter.mq.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class DataMqReliableMessageServiceImpl implements IDataMqReliableMessageService {

    @Autowired
    private IDataCommonDao commonDao;

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private IDataMqDeadMessageDao mqDeadMessageDao;

    @Autowired
    private IDataMqReliableMessageDao reliableMessageDao;

    @Autowired
    private IDataMqMessageExternalDao mqMessageExternalDao;


    @Override
    public void create(MqReliableMessageCreateInputDto inputDto) {
        DataMqReliableMessageEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataMqReliableMessageEntity.class);
        String reliableMessageId = reliableMessageDao.create(insertEntity);

        DataMqMessageExternalEntity insertExternalEntity = new DataMqMessageExternalEntity();
        insertExternalEntity.setTableName("t_mq_reliable_message");
        insertExternalEntity.setDataId(reliableMessageId);
        insertExternalEntity.setContent(inputDto.getContent());
        insertExternalEntity.setFailReason(inputDto.getFailReason());
        mqMessageExternalDao.create(insertExternalEntity);
    }

    @Override
    public void delete(String id) {
        reliableMessageDao.delete(id);
        mqMessageExternalDao.deleteByReliableMessageId(id);
    }

    @Override
    public void update4Exception(MqReliableMessageUpdate4ExceptionInputDto inputDto) {
        reliableMessageDao.update4Exception(inputDto.getId(), inputDto.getNextUnixTimestamp());
        mqMessageExternalDao.updateFailReasonByReliableMessageId(inputDto.getId(), inputDto.getFailReason());
    }

    @Override
    public void resendMessage() {
        Long unixTimestamp = commonDao.getUnixTimestamp();

        while (true) {
            List<DataMqReliableMessageEntity> messageReliableList = reliableMessageDao.dynamicSelect(unixTimestamp);

            if (CollectionUtils.isEmpty(messageReliableList)) {
                return;
            }

            for (DataMqReliableMessageEntity reliableMessage : messageReliableList) {

                //默认5秒
                long nextUnixTimestamp;
                String reliableMessageId = reliableMessage.getId();

                String retryStrategy = reliableMessage.getRetryStrategy();
                List<Integer> retryList = JSONUtil.toList(retryStrategy, Integer.class);

                if (reliableMessage.getConsumerTimes() > retryList.size()) {
                    //超过最大重发次数或者消费次数超过
                    deadMessage(reliableMessageId);
                    continue;
                } else {
                    //发送次数超过消费次数（防止mq队列阻塞引起可靠消息被标记为死亡，预计时间和保险时间取较大的）
                    if (reliableMessage.getResendTimes() >= reliableMessage.getConsumerTimes()) {
                        //保险时间
                        long timeMillis = reliableMessage.getNextExeTime() - reliableMessage.getLastConsumerTime();
                        nextUnixTimestamp = (reliableMessage.getResendTimes() - reliableMessage.getConsumerTimes() + 1) * timeMillis;

                        //预计时间
                        if (reliableMessage.getResendTimes() < retryList.size()) {
                            long nextExpectationUnixTimestamp = retryList.get(reliableMessage.getResendTimes()) * 1000L;
                            if (nextExpectationUnixTimestamp > nextUnixTimestamp) {
                                nextUnixTimestamp = nextExpectationUnixTimestamp;
                            }
                        }
                    } else {
                        nextUnixTimestamp = retryList.get(reliableMessage.getResendTimes()) * 1000L;
                    }
                }

                int count = reliableMessageDao.update4Resend(reliableMessageId, reliableMessage.getUpdateTime(), nextUnixTimestamp);
                if (count > 0) {
                    //查询内容
                    DataMqMessageExternalEntity externalEntity = mqMessageExternalDao.getByReliableMessageId(reliableMessageId);

                    //发送可靠消息
                    streamBridge.send(reliableMessage.getProducerName(),
                            MessageBuilder
                                    .withPayload(externalEntity.getContent())
                                    .setHeader(MqConstant.HEAD_KEY_PRODUCER_NAME, reliableMessage.getProducerName())
                                    .setHeader(MqConstant.HEAD_KEY_RELIABLE_MESSAGE, JSONUtil.toJsonStr(reliableMessage))
                                    .setHeader(MqConstant.HEAD_KEY_MESSAGE_UUID, reliableMessage.getMessageUuid())
                                    .build());
                }
            }
        }
    }

    @Override
    public Boolean contain(String id) {
        return reliableMessageDao.contain(id);
    }

    @Override
    public MqReliableMessageDetailDto getById(String id) {
        DataMqReliableMessageEntity messageEntity = reliableMessageDao.getById(id);
        if (null == messageEntity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(messageEntity), MqReliableMessageDetailDto.class);
    }

    private void deadMessage(String reliableMessageId) {
        log.info("添加死亡消息 {}", reliableMessageId);
        if (null == reliableMessageId) {
            return;
        }
        mqDeadMessageDao.insertByReliableMessageId(reliableMessageId);
        reliableMessageDao.delete(reliableMessageId);
        mqMessageExternalDao.updateTableNameByReliableMessageId(reliableMessageId);
    }
}
