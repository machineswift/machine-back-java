package com.machine.service.data.mesage.dao.impl;

import com.machine.service.data.mesage.dao.IDataMqReliableMessageDao;
import com.machine.service.data.mesage.dao.mapper.DataMqReliableMessageMapper;
import com.machine.service.data.mesage.dao.mapper.entity.DataMqReliableMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataMqReliableMessageDaoImpl implements IDataMqReliableMessageDao {

    @Autowired
    private DataMqReliableMessageMapper reliableMessageMapper;

    @Override
    public String create(DataMqReliableMessageEntity insertEntity) {
        reliableMessageMapper.insert(insertEntity);
        return insertEntity.getId();
    }

    @Override
    public void delete(String id) {
        reliableMessageMapper.deleteById(id);
    }

    @Override
    public void update4Exception(String id,
                                 Long nextUnixTimestamp) {
        reliableMessageMapper.update4Exception(id, nextUnixTimestamp);
    }

    @Override
    public int update4Resend(String id,
                             long updateTime,
                             Long nextUnixTimestamp) {
        return reliableMessageMapper.update4Resend(id, updateTime, nextUnixTimestamp);
    }

    @Override
    public Boolean contain(String id) {
        return reliableMessageMapper.contain(id);
    }

    @Override
    public DataMqReliableMessageEntity getById(String id) {
        return reliableMessageMapper.selectById(id);
    }

    @Override
    public List<DataMqReliableMessageEntity> dynamicSelect(Long unixTimestamp) {
        return reliableMessageMapper.dynamicSelect(unixTimestamp);
    }
}
