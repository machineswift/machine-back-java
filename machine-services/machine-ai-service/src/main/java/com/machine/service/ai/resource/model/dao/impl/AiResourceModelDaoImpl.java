package com.machine.service.ai.resource.model.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelQueryPageInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.service.ai.resource.model.dao.IAiResourceModelDao;
import com.machine.service.ai.resource.model.dao.mapper.AiResourceModelMapper;
import com.machine.service.ai.resource.model.dao.mapper.entity.AiResourceModelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AiResourceModelDaoImpl implements IAiResourceModelDao {

    @Autowired
    private AiResourceModelMapper aiResourceModelMapper;

    @Override
    public String insert(AiResourceModelEntity entity) {
        aiResourceModelMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int deleteById(String id) {
        return aiResourceModelMapper.deleteById(id);
    }

    @Override
    public int update(AiResourceModelEntity entity) {
        return aiResourceModelMapper.updateById(entity);
    }

    @Override
    public int updateStatus(String id,
                            StatusEnum status) {
        AiResourceModelEntity updateEntity = new AiResourceModelEntity();
        updateEntity.setId(id);
        updateEntity.setStatus(status);
        return aiResourceModelMapper.updateById(updateEntity);
    }

    @Override
    public AiResourceModelEntity getById(String id) {
        return aiResourceModelMapper.selectById(id);
    }

    @Override
    public AiResourceModelEntity getByProviderIdAndCode(String providerId,
                                                        String code) {
        Wrapper<AiResourceModelEntity> wrapper = new LambdaQueryWrapper<AiResourceModelEntity>()
                .eq(AiResourceModelEntity::getProviderId, providerId)
                .eq(AiResourceModelEntity::getCode, code);
        return aiResourceModelMapper.selectOne(wrapper);
    }

    @Override
    public Page<AiResourceModelEntity> selectPage(AiResourceModelQueryPageInputDto inputDto) {
        IPage<AiResourceModelEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return aiResourceModelMapper.selectPage(inputDto, page);
    }
}
