package com.machine.service.data.mesage.dao.impl;

import com.machine.service.data.mesage.dao.IMqDeadMessageDao;
import com.machine.service.data.mesage.dao.mapper.MqDeadMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MqDeadMessageDaoImpl implements IMqDeadMessageDao {

    @Autowired
    private MqDeadMessageMapper deadMessageMapper;


    @Override
    public void insertByReliableMessageId(String reliableMessageId) {
        deadMessageMapper.insertByReliableMessageId(reliableMessageId);
    }
}
