package com.machine.service.data.mesage.dao;

public interface IDataMqDeadMessageDao {
    void insertByReliableMessageId(String reliableMessageId);
}
