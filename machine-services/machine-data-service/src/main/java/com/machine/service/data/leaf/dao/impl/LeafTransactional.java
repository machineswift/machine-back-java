package com.machine.service.data.leaf.dao.impl;

import com.machine.service.data.leaf.dao.mapper.LeafMapper;
import com.machine.service.data.leaf.dao.mapper.entity.LeafEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LeafTransactional {

    @Autowired
    private LeafMapper leafMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insert(LeafEntity entity) {
        leafMapper.insert(entity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Integer updateMaxId(LeafEntity leafAlloc) {
        return leafMapper.updateMaxId(leafAlloc.getBizTag(), leafAlloc.getMaxId());
    }

}
