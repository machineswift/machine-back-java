package com.machine.service.data.leaf.dao.impl;

import com.machine.service.data.leaf.dao.mapper.DataLeafMapper;
import com.machine.service.data.leaf.dao.mapper.entity.DataLeafEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DataLeafTransactional {

    @Autowired
    private DataLeafMapper dataLeafMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insert(DataLeafEntity entity) {
        dataLeafMapper.insert(entity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Integer updateMaxId(DataLeafEntity leafAlloc) {
        return dataLeafMapper.updateMaxId(leafAlloc.getBizTag(), leafAlloc.getMaxId());
    }

}
