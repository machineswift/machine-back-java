package com.machine.service.data.external.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.data.external.dto.input.DataExternalFieldDataGetValueInputDto;
import com.machine.service.data.external.dao.mapper.DataExternalFieldDataMapper;
import com.machine.service.data.external.dao.IDataExternalFieldDataDao;
import com.machine.service.data.external.dao.mapper.entity.DataExternalFieldDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataExternalFieldDataDaoImpl implements IDataExternalFieldDataDao {

    @Autowired
    private DataExternalFieldDataMapper dataExternalFieldDataMapper;

    @Override
    public String insert(DataExternalFieldDataEntity entity) {
        dataExternalFieldDataMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int delete(DataExternalFieldDataEntity entity) {
        Wrapper<DataExternalFieldDataEntity> deleteWrapper = new LambdaQueryWrapper<DataExternalFieldDataEntity>()
                .eq(DataExternalFieldDataEntity::getTableName, entity.getTableName())
                .eq(DataExternalFieldDataEntity::getFieldName, entity.getFieldName())
                .eq(DataExternalFieldDataEntity::getDataId, entity.getDataId());
        return dataExternalFieldDataMapper.delete(deleteWrapper);
    }

    @Override
    public String getValue(DataExternalFieldDataGetValueInputDto inputDto) {
        Wrapper<DataExternalFieldDataEntity> deleteWrapper = new LambdaQueryWrapper<DataExternalFieldDataEntity>()
                .eq(DataExternalFieldDataEntity::getTableName, inputDto.getTableName())
                .eq(DataExternalFieldDataEntity::getFieldName, inputDto.getFieldName())
                .eq(DataExternalFieldDataEntity::getDataId, inputDto.getDataId());
        DataExternalFieldDataEntity entity = dataExternalFieldDataMapper.selectOne(deleteWrapper);
        if (entity == null) {
            return null;
        }
        return entity.getExternalValue();
    }
}
