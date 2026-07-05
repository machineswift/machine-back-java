package com.machine.service.ai.resource.model.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.ai.AiProviderEnum;
import com.machine.service.ai.resource.model.dao.IAiResourceProviderDao;
import com.machine.service.ai.resource.model.dao.mapper.AiResourceProviderMapper;
import com.machine.service.ai.resource.model.dao.mapper.entity.AiResourceProviderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AiResourceProviderDaoImpl implements IAiResourceProviderDao {

    @Autowired
    private AiResourceProviderMapper aiResourceProviderMapper;

    @Override
    public String insert(AiResourceProviderEntity entity) {
        aiResourceProviderMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int deleteById(String id) {
        return aiResourceProviderMapper.deleteById(id);
    }

    @Override
    public int update(AiResourceProviderEntity entity) {
        return aiResourceProviderMapper.updateById(entity);
    }

    @Override
    public int updateStatus(String id,
                            StatusEnum status) {
        AiResourceProviderEntity updateEntity = new AiResourceProviderEntity();
        updateEntity.setId(id);
        updateEntity.setStatus(status);
        return aiResourceProviderMapper.updateById(updateEntity);
    }

    @Override
    public AiResourceProviderEntity getById(String id) {
        return aiResourceProviderMapper.selectById(id);
    }

    @Override
    public AiResourceProviderEntity getByProvider(AiProviderEnum provider) {
        Wrapper<AiResourceProviderEntity> wrapper = new LambdaQueryWrapper<AiResourceProviderEntity>()
                .eq(AiResourceProviderEntity::getProvider, provider);
        return aiResourceProviderMapper.selectOne(wrapper);
    }

    @Override
    public List<AiResourceProviderEntity> list(StatusEnum status) {
        Wrapper<AiResourceProviderEntity> wrapper = new LambdaQueryWrapper<AiResourceProviderEntity>()
                .eq(status != null, AiResourceProviderEntity::getStatus, status)
                .orderByDesc(AiResourceProviderEntity::getCreateTime);
        return aiResourceProviderMapper.selectList(wrapper);
    }
}
