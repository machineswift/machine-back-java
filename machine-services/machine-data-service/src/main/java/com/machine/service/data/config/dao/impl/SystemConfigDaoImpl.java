package com.machine.service.data.config.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.config.dao.ISystemConfigDao;
import com.machine.service.data.config.dao.mapper.SystemConfigMapper;
import com.machine.service.data.config.dao.mapper.entity.SystemConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SystemConfigDaoImpl implements ISystemConfigDao {

    @Autowired
    private SystemConfigMapper configMapper;

    @Override
    public void insert(SystemConfigEntity entity) {
        configMapper.insert(entity);
    }

    @Override
    public int delete(String id) {
        return configMapper.deleteById(id);
    }

    @Override
    public int update(SystemConfigEntity updateEntity) {
        return configMapper.updateById(updateEntity);
    }


    @Override
    public SystemConfigEntity getByUk(String category, String code) {
        Wrapper<SystemConfigEntity> queryWrapper = new LambdaQueryWrapper<SystemConfigEntity>()
                .eq(SystemConfigEntity::getCategory, category)
                .eq(SystemConfigEntity::getCode, code);
        return configMapper.selectOne(queryWrapper);
    }

}
