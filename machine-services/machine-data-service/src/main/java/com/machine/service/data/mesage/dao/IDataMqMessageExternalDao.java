package com.machine.service.data.mesage.dao;

import com.machine.service.data.mesage.dao.mapper.entity.DataMqMessageExternalEntity;

public interface IDataMqMessageExternalDao {

    String create(DataMqMessageExternalEntity insertExternalEntity);

    void deleteByReliableMessageId(String id);

    void updateTableNameByReliableMessageId(String reliableMessageId);

    void updateFailReasonByReliableMessageId(String reliableMessageId,
                                             String failReason);

    DataMqMessageExternalEntity getByReliableMessageId(String reliableMessageId);

}
