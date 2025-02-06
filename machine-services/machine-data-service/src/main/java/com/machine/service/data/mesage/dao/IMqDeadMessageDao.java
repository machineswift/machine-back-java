package com.machine.service.data.mesage.dao;

public interface IMqDeadMessageDao {
    void insertByReliableMessageId(String reliableMessageId);
}
