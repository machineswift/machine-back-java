package com.machine.service.data.material.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.material.dao.IDataMaterialImageDao;
import com.machine.service.data.material.dao.mapper.DataMaterialImageMapper;
import com.machine.service.data.material.dao.mapper.entity.DataMaterialImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataMaterialImageDaoImpl implements IDataMaterialImageDao {

    @Autowired
    private DataMaterialImageMapper materialImageMapper;

    @Override
    public void insert(DataMaterialImageEntity entity) {
        materialImageMapper.insert(entity);
    }

    @Override
    public int deleteById(String id) {
        return materialImageMapper.deleteById(id);
    }

    @Override
    public int deleteByMaterialId(String materialId) {
        Wrapper<DataMaterialImageEntity> wrapper = new LambdaQueryWrapper<DataMaterialImageEntity>()
                .eq(DataMaterialImageEntity::getMaterialId, materialId);
        return materialImageMapper.delete(wrapper);
    }

    @Override
    public DataMaterialImageEntity getById(String id) {
        return materialImageMapper.selectById(id);
    }

    @Override
    public DataMaterialImageEntity getByMaterialId(String materialId) {
        Wrapper<DataMaterialImageEntity> wrapper = new LambdaQueryWrapper<DataMaterialImageEntity>()
                .eq(DataMaterialImageEntity::getMaterialId, materialId);
        return materialImageMapper.selectOne(wrapper);
    }

    @Override
    public List<DataMaterialImageEntity> selectByMaterialIdSet(Set<String> materialIdSet) {
        Wrapper<DataMaterialImageEntity> wrapper = new LambdaQueryWrapper<DataMaterialImageEntity>()
                .in(DataMaterialImageEntity::getMaterialId, materialIdSet);
        return materialImageMapper.selectList(wrapper);
    }
}
