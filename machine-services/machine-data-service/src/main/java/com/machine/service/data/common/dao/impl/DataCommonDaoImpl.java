package com.machine.service.data.common.dao.impl;

import com.machine.service.data.common.dao.IDataCommonDao;
import com.machine.service.data.common.dao.mapper.DataCommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataCommonDaoImpl implements IDataCommonDao {

    @Autowired
    private DataCommonMapper dataCommonMapper;

    @Override
    public Long getUnixTimestamp() {
        return dataCommonMapper.getUnixTimestamp().getTime();
    }
}
