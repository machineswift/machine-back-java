package com.machine.service.data.leaf.dao;

import com.machine.service.data.leaf.dao.mapper.entity.DataLeafEntity;

public interface IDataLeafDao {
    DataLeafEntity updateMaxId(DataLeafEntity entity);
}
