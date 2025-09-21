package com.machine.service.data.config.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.config.dao.IDataSystemConfigDao;
import com.machine.service.data.config.dao.mapper.DataSystemConfigMapper;
import com.machine.service.data.config.dao.mapper.entity.DataSystemConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataSystemConfigDaoImpl implements IDataSystemConfigDao {

    @Autowired
    private DataSystemConfigMapper configMapper;

    @Override
    public void insert(DataSystemConfigEntity entity) {
        configMapper.insert(entity);
    }

    @Override
    public int delete(String id) {
        return configMapper.deleteById(id);
    }

    @Override
    public int update(DataSystemConfigEntity updateEntity) {
        return configMapper.updateById(updateEntity);
    }


    @Override
    public DataSystemConfigEntity getByUk(String category, String code) {
        Wrapper<DataSystemConfigEntity> queryWrapper = new LambdaQueryWrapper<DataSystemConfigEntity>()
                .eq(DataSystemConfigEntity::getCategory, category)
                .eq(DataSystemConfigEntity::getCode, code);
        return configMapper.selectOne(queryWrapper);
    }

}
