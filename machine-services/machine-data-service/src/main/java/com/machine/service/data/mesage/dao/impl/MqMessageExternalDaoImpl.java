package com.machine.service.data.mesage.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.mesage.dao.IMqMessageExternalDao;
import com.machine.service.data.mesage.dao.mapper.MqMessageExternalMapper;
import com.machine.service.data.mesage.dao.mapper.entity.MqMessageExternalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MqMessageExternalDaoImpl implements IMqMessageExternalDao {

    @Autowired
    private MqMessageExternalMapper mqMessageExternalMapper;

    @Override
    public String create(MqMessageExternalEntity insertExternalEntity) {
        mqMessageExternalMapper.insert(insertExternalEntity);
        return insertExternalEntity.getId();
    }

    @Override
    public void updateTableNameByReliableMessageId(String reliableMessageId) {
        mqMessageExternalMapper.updateTableNameByReliableMessageId(reliableMessageId);
    }

    @Override
    public void updateFailReasonByReliableMessageId(String reliableMessageId,
                                                    String failReason) {
        mqMessageExternalMapper.updateFailReasonByReliableMessageId(reliableMessageId, failReason);
    }

    @Override
    public void deleteByReliableMessageId(String id) {
        mqMessageExternalMapper.deleteByReliableMessageId(id);
    }

    @Override
    public MqMessageExternalEntity getByReliableMessageId(String reliableMessageId) {
        Wrapper<MqMessageExternalEntity> queryWrapper = new LambdaQueryWrapper<MqMessageExternalEntity>()
                .eq(MqMessageExternalEntity::getTableName, reliableMessageId)
                .eq(MqMessageExternalEntity::getTableName, "t_mq_reliable_message");
        return mqMessageExternalMapper.selectOne(queryWrapper);
    }
}
