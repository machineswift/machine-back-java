package com.machine.service.data.mesage.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.mesage.dao.IDataMqMessageExternalDao;
import com.machine.service.data.mesage.dao.mapper.DataMqMessageExternalMapper;
import com.machine.service.data.mesage.dao.mapper.entity.DataMqMessageExternalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataMqMessageExternalDaoImpl implements IDataMqMessageExternalDao {

    @Autowired
    private DataMqMessageExternalMapper dataMqMessageExternalMapper;

    @Override
    public String create(DataMqMessageExternalEntity insertExternalEntity) {
        dataMqMessageExternalMapper.insert(insertExternalEntity);
        return insertExternalEntity.getId();
    }

    @Override
    public void updateTableNameByReliableMessageId(String reliableMessageId) {
        dataMqMessageExternalMapper.updateTableNameByReliableMessageId(reliableMessageId);
    }

    @Override
    public void updateFailReasonByReliableMessageId(String reliableMessageId,
                                                    String failReason) {
        dataMqMessageExternalMapper.updateFailReasonByReliableMessageId(reliableMessageId, failReason);
    }

    @Override
    public void deleteByReliableMessageId(String id) {
        dataMqMessageExternalMapper.deleteByReliableMessageId(id);
    }

    @Override
    public DataMqMessageExternalEntity getByReliableMessageId(String reliableMessageId) {
        Wrapper<DataMqMessageExternalEntity> queryWrapper = new LambdaQueryWrapper<DataMqMessageExternalEntity>()
                .eq(DataMqMessageExternalEntity::getTableName, reliableMessageId)
                .eq(DataMqMessageExternalEntity::getTableName, "t_mq_reliable_message");
        return dataMqMessageExternalMapper.selectOne(queryWrapper);
    }
}
