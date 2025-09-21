package com.machine.service.data.material.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.material.dao.IDataMaterialVideoDao;
import com.machine.service.data.material.dao.mapper.DataMaterialVideoMapper;
import com.machine.service.data.material.dao.mapper.entity.DataMaterialVideoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataMaterialVideoDaoImpl implements IDataMaterialVideoDao {

    @Autowired
    private DataMaterialVideoMapper materialVideoMapper;

    @Override
    public void insert(DataMaterialVideoEntity entity) {
        materialVideoMapper.insert(entity);
    }

    @Override
    public int deleteById(String id) {
        return materialVideoMapper.deleteById(id);
    }

    @Override
    public int deleteByMaterialId(String materialId) {
        Wrapper<DataMaterialVideoEntity> wrapper = new LambdaQueryWrapper<DataMaterialVideoEntity>()
                .eq(DataMaterialVideoEntity::getMaterialId, materialId);
        return materialVideoMapper.delete(wrapper);
    }

    @Override
    public DataMaterialVideoEntity getById(String id) {
        return materialVideoMapper.selectById(id);
    }

    @Override
    public DataMaterialVideoEntity getByMaterialId(String materialId) {
        Wrapper<DataMaterialVideoEntity> wrapper = new LambdaQueryWrapper<DataMaterialVideoEntity>()
                .eq(DataMaterialVideoEntity::getMaterialId, materialId);
        return materialVideoMapper.selectOne(wrapper);
    }

    @Override
    public List<DataMaterialVideoEntity> selectByMaterialIdSet(Set<String> materialIdSet) {
        Wrapper<DataMaterialVideoEntity> wrapper = new LambdaQueryWrapper<DataMaterialVideoEntity>()
                .in(DataMaterialVideoEntity::getMaterialId, materialIdSet);
        return materialVideoMapper.selectList(wrapper);
    }
}
