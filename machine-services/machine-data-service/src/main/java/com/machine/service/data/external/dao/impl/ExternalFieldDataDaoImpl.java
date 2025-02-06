package com.machine.service.data.external.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.data.external.dto.input.ExternalFieldDataGetValueInputDto;
import com.machine.service.data.external.dao.mapper.ExternalFieldDataMapper;
import com.machine.service.data.external.dao.IExternalFieldDataDao;
import com.machine.service.data.external.dao.mapper.entity.ExternalFieldDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalFieldDataDaoImpl implements IExternalFieldDataDao {

    @Autowired
    private ExternalFieldDataMapper externalFieldDataMapper;

    @Override
    public String insert(ExternalFieldDataEntity entity) {
        externalFieldDataMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int delete(ExternalFieldDataEntity entity) {
        Wrapper<ExternalFieldDataEntity> deleteWrapper = new LambdaQueryWrapper<ExternalFieldDataEntity>()
                .eq(ExternalFieldDataEntity::getTableName, entity.getTableName())
                .eq(ExternalFieldDataEntity::getFieldName, entity.getFieldName())
                .eq(ExternalFieldDataEntity::getDataId, entity.getDataId());
        return externalFieldDataMapper.delete(deleteWrapper);
    }

    @Override
    public String getValue(ExternalFieldDataGetValueInputDto inputDto) {
        Wrapper<ExternalFieldDataEntity> deleteWrapper = new LambdaQueryWrapper<ExternalFieldDataEntity>()
                .eq(ExternalFieldDataEntity::getTableName, inputDto.getTableName())
                .eq(ExternalFieldDataEntity::getFieldName, inputDto.getFieldName())
                .eq(ExternalFieldDataEntity::getDataId, inputDto.getDataId());
        ExternalFieldDataEntity entity = externalFieldDataMapper.selectOne(deleteWrapper);
        if (entity == null) {
            return null;
        }
        return entity.getExternalValue();
    }
}
