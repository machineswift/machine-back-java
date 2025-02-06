package com.machine.service.data.config.service.impl;

import com.machine.client.data.config.dto.SystemConfigDto;
import com.machine.service.data.config.dao.ISystemConfigDao;
import com.machine.service.data.config.dao.mapper.entity.SystemConfigEntity;
import com.machine.service.data.config.service.ISystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SystemConfigServiceImpl implements ISystemConfigService {

    @Autowired
    private ISystemConfigDao configDao;

    @Override
    public String create(SystemConfigDto dto) {
        SystemConfigEntity entity = configDao.getByUk(dto.getCategory(), dto.getCode());
        if (entity == null) {
            SystemConfigEntity insertEntity = new SystemConfigEntity();
            insertEntity.setCategory(dto.getCategory());
            insertEntity.setCode(dto.getCode());
            insertEntity.setContent(dto.getContent());
            configDao.insert(insertEntity);
            return insertEntity.getId();
        } else {
            SystemConfigEntity updateEntity = new SystemConfigEntity();
            updateEntity.setId(entity.getId());
            updateEntity.setContent(dto.getContent());
            configDao.update(updateEntity);
            return updateEntity.getId();
        }
    }

    @Override
    public int delete(SystemConfigDto dto) {
        SystemConfigEntity entity = configDao.getByUk(dto.getCategory(), dto.getCode());
        if (null == entity) {
            return 0;
        }
        return configDao.delete(entity.getId());
    }

    @Override
    public int update(SystemConfigDto dto) {
        SystemConfigEntity entity = configDao.getByUk(dto.getCategory(), dto.getCode());
        if (null == entity) {
            return 0;
        }

        SystemConfigEntity updateEntity = new SystemConfigEntity();
        updateEntity.setId(entity.getId());
        updateEntity.setContent(dto.getContent());
        return configDao.update(updateEntity);
    }

    @Override
    public SystemConfigDto getByUk(SystemConfigDto dto) {
        SystemConfigEntity entity = configDao.getByUk(dto.getCategory(), dto.getCode());
        if (null == entity) {
            return null;
        }
        return new SystemConfigDto(entity.getCategory(), entity.getCode(), entity.getContent());
    }
}
