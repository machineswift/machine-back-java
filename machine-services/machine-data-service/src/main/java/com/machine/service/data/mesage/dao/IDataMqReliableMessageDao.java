package com.machine.service.data.mesage.dao;

import com.machine.service.data.mesage.dao.mapper.entity.DataMqReliableMessageEntity;

import java.util.List;

public interface IDataMqReliableMessageDao {

    String create(DataMqReliableMessageEntity insertEntity);

    void delete(String id);

    void update4Exception(String id,
                          Long nextUnixTimestamp);

    int update4Resend(String id,
                      long updateTime,
                      Long nextUnixTimestamp);

    Boolean contain(String id);

    DataMqReliableMessageEntity getById(String id);

    List<DataMqReliableMessageEntity> dynamicSelect(Long unixTimestamp);

}
