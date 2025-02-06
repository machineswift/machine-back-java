package com.machine.service.data.mesage.dao;

import com.machine.service.data.mesage.dao.mapper.entity.MqMessageExternalEntity;

public interface IMqMessageExternalDao {

    String create(MqMessageExternalEntity insertExternalEntity);

    void updateTableNameByReliableMessageId(String reliableMessageId);

    void updateFailReasonByReliableMessageId(String reliableMessageId,
                                             String failReason);

    void deleteByReliableMessageId(String id);

    MqMessageExternalEntity getByReliableMessageId(String reliableMessageId);

}
