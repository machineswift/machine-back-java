package com.machine.service.data.leaf.dao;

import com.machine.service.data.leaf.dao.mapper.entity.LeafEntity;

public interface ILeafDao {
    LeafEntity updateMaxId(LeafEntity entity);
}
