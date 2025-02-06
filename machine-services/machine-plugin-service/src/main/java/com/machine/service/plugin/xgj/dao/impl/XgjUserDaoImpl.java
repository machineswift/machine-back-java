package com.machine.service.plugin.xgj.dao.impl;

import com.machine.service.plugin.xgj.dao.IXgjUserDao;
import com.machine.service.plugin.xgj.dao.mapper.IXgjUserMapper;
import com.machine.service.plugin.xgj.dao.mapper.entity.XgjUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class XgjUserDaoImpl implements IXgjUserDao {

    @Autowired
    private IXgjUserMapper xgjUserMapper;


    @Override
    public List<XgjUserEntity> listByOffset(Long offset,
                                            int limit) {
        return xgjUserMapper.listByOffset(offset, limit);
    }
}
