package com.machine.service.data.config.dao;

import com.machine.service.data.config.dao.mapper.entity.SystemConfigEntity;

public interface ISystemConfigDao {

    void insert(SystemConfigEntity entity);

    int delete(String id);

    int update(SystemConfigEntity updateEntity);

    SystemConfigEntity getByUk(String category, String code);

}
