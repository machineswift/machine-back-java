package com.machine.service.data.config.service.impl;

import com.machine.client.data.config.dto.DataSystemConfigDto;
import com.machine.service.data.config.dao.IDataSystemConfigDao;
import com.machine.service.data.config.dao.mapper.entity.DataSystemConfigEntity;
import com.machine.service.data.config.service.IDataSystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataSystemConfigServiceImpl implements IDataSystemConfigService {

    @Autowired
    private IDataSystemConfigDao configDao;

    @Override
    public String create(DataSystemConfigDto dto) {
        DataSystemConfigEntity entity = configDao.getByUk(dto.getCategory(), dto.getCode());
        if (entity == null) {
            DataSystemConfigEntity insertEntity = new DataSystemConfigEntity();
            insertEntity.setCategory(dto.getCategory());
            insertEntity.setCode(dto.getCode());
            insertEntity.setContent(dto.getContent());
            configDao.insert(insertEntity);
            return insertEntity.getId();
        } else {
            DataSystemConfigEntity updateEntity = new DataSystemConfigEntity();
            updateEntity.setId(entity.getId());
            updateEntity.setContent(dto.getContent());
            configDao.update(updateEntity);
            return updateEntity.getId();
        }
    }

    @Override
    public int delete(DataSystemConfigDto dto) {
        DataSystemConfigEntity entity = configDao.getByUk(dto.getCategory(), dto.getCode());
        if (null == entity) {
            return 0;
        }
        return configDao.delete(entity.getId());
    }

    @Override
    public int update(DataSystemConfigDto dto) {
        DataSystemConfigEntity entity = configDao.getByUk(dto.getCategory(), dto.getCode());
        if (null == entity) {
            return 0;
        }

        DataSystemConfigEntity updateEntity = new DataSystemConfigEntity();
        updateEntity.setId(entity.getId());
        updateEntity.setContent(dto.getContent());
        return configDao.update(updateEntity);
    }

    @Override
    public DataSystemConfigDto getByUk(DataSystemConfigDto dto) {
        DataSystemConfigEntity entity = configDao.getByUk(dto.getCategory(), dto.getCode());
        if (null == entity) {
            return null;
        }
        return new DataSystemConfigDto(entity.getCategory(), entity.getCode(), entity.getContent());
    }
}
