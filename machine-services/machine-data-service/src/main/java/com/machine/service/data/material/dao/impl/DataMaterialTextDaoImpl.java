package com.machine.service.data.material.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.material.dao.IDataMaterialTextDao;
import com.machine.service.data.material.dao.mapper.DataMaterialTextMapper;
import com.machine.service.data.material.dao.mapper.entity.DataMaterialTextEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataMaterialTextDaoImpl implements IDataMaterialTextDao {

    @Autowired
    private DataMaterialTextMapper materialTextMapper;

    @Override
    public void insert(DataMaterialTextEntity entity) {
        materialTextMapper.insert(entity);
    }

    @Override
    public int deleteById(String id) {
        return materialTextMapper.deleteById(id);
    }

    @Override
    public int deleteByMaterialId(String materialId) {
        Wrapper<DataMaterialTextEntity> wrapper = new LambdaQueryWrapper<DataMaterialTextEntity>()
                .eq(DataMaterialTextEntity::getMaterialId, materialId);
        return materialTextMapper.delete(wrapper);
    }

    @Override
    public DataMaterialTextEntity getById(String id) {
        return materialTextMapper.selectById(id);
    }

    @Override
    public DataMaterialTextEntity getByMaterialId(String materialId) {
        Wrapper<DataMaterialTextEntity> wrapper = new LambdaQueryWrapper<DataMaterialTextEntity>()
                .eq(DataMaterialTextEntity::getMaterialId, materialId);
        return materialTextMapper.selectOne(wrapper);
    }

    @Override
    public List<DataMaterialTextEntity> selectByMaterialIdSet(Set<String> materialIdSet) {
        Wrapper<DataMaterialTextEntity> wrapper = new LambdaQueryWrapper<DataMaterialTextEntity>()
                .in(DataMaterialTextEntity::getMaterialId, materialIdSet);
        return materialTextMapper.selectList(wrapper);
    }
}
