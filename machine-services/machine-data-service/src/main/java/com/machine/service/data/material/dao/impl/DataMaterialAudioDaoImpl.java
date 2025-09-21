package com.machine.service.data.material.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.material.dao.IDataMaterialAudioDao;
import com.machine.service.data.material.dao.mapper.DataMaterialAudioMapper;
import com.machine.service.data.material.dao.mapper.entity.DataMaterialAudioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataMaterialAudioDaoImpl implements IDataMaterialAudioDao {

    @Autowired
    private DataMaterialAudioMapper materialAudioMapper;

    @Override
    public void insert(DataMaterialAudioEntity entity) {
        materialAudioMapper.insert(entity);
    }

    @Override
    public int deleteById(String id) {
        return materialAudioMapper.deleteById(id);
    }

    @Override
    public int deleteByMaterialId(String materialId) {
        Wrapper<DataMaterialAudioEntity> wrapper = new LambdaQueryWrapper<DataMaterialAudioEntity>()
                .eq(DataMaterialAudioEntity::getMaterialId, materialId);
        return materialAudioMapper.delete(wrapper);
    }

    @Override
    public DataMaterialAudioEntity getById(String id) {
        return materialAudioMapper.selectById(id);
    }

    @Override
    public DataMaterialAudioEntity getByMaterialId(String materialId) {
        Wrapper<DataMaterialAudioEntity> queryWrapper = new LambdaQueryWrapper<DataMaterialAudioEntity>()
                .eq(DataMaterialAudioEntity::getMaterialId, materialId);
        return materialAudioMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DataMaterialAudioEntity> selectByMaterialIdSet(Set<String> materialIdSet) {
        Wrapper<DataMaterialAudioEntity> queryWrapper = new LambdaQueryWrapper<DataMaterialAudioEntity>()
                .in(DataMaterialAudioEntity::getMaterialId, materialIdSet);
        return materialAudioMapper.selectList(queryWrapper);
    }
}
