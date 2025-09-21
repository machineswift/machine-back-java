package com.machine.service.data.mesage.dao.impl;

import com.machine.service.data.mesage.dao.IDataMqDeadMessageDao;
import com.machine.service.data.mesage.dao.mapper.DataMqDeadMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataMqDeadMessageDaoImpl implements IDataMqDeadMessageDao {

    @Autowired
    private DataMqDeadMessageMapper deadMessageMapper;


    @Override
    public void insertByReliableMessageId(String reliableMessageId) {
        deadMessageMapper.insertByReliableMessageId(reliableMessageId);
    }
}
