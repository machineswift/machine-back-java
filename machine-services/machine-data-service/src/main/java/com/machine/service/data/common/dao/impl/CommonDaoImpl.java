package com.machine.service.data.common.dao.impl;

import com.machine.service.data.common.dao.ICommonDao;
import com.machine.service.data.common.dao.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDaoImpl implements ICommonDao {

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public Long getUnixTimestamp() {
        return commonMapper.getUnixTimestamp().getTime();
    }
}
