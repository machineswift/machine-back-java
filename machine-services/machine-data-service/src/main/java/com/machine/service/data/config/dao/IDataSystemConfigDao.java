package com.machine.service.data.config.dao;

import com.machine.service.data.config.dao.mapper.entity.DataSystemConfigEntity;

public interface IDataSystemConfigDao {

    void insert(DataSystemConfigEntity entity);

    int delete(String id);

    int update(DataSystemConfigEntity updateEntity);

    DataSystemConfigEntity getByUk(String category, String code);

}
