package com.machine.service.data.mesage.dao;

import com.machine.service.data.mesage.dao.mapper.entity.MqReliableMessageEntity;

import java.util.List;

public interface IMqReliableMessageDao {

    String create(MqReliableMessageEntity insertEntity);

    void delete(String id);

    void update4Exception(String id,
                          Long nextUnixTimestamp);

    int update4Resend(String id,
                      long updateTime,
                      Long nextUnixTimestamp);

    Boolean contain(String id);

    MqReliableMessageEntity getById(String id);

    List<MqReliableMessageEntity> dynamicSelect(Long unixTimestamp);

}
