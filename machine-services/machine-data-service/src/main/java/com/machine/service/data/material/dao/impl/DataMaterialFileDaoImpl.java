package com.machine.service.data.material.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.material.dao.IDataMaterialFileDao;
import com.machine.service.data.material.dao.mapper.DataMaterialFileMapper;
import com.machine.service.data.material.dao.mapper.entity.DataMaterialFileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataMaterialFileDaoImpl implements IDataMaterialFileDao {
    
    @Autowired
    private DataMaterialFileMapper materialFileMapper;

    @Override
    public void insert(DataMaterialFileEntity entity) {
        materialFileMapper.insert(entity);
    }

    @Override
    public int deleteById(String id) {
       return materialFileMapper.deleteById(id);
    }

    @Override
    public int deleteByMaterialId(String materialId) {
        Wrapper<DataMaterialFileEntity> wrapper = new LambdaQueryWrapper<DataMaterialFileEntity>()
                .eq(DataMaterialFileEntity::getMaterialId, materialId);
        return materialFileMapper.delete(wrapper);
    }

    @Override
    public DataMaterialFileEntity getById(String id) {
        return materialFileMapper.selectById(id);
    }

    @Override
    public DataMaterialFileEntity getByMaterialId(String materialId) {
        Wrapper<DataMaterialFileEntity> wrapper = new LambdaQueryWrapper<DataMaterialFileEntity>()
                .eq(DataMaterialFileEntity::getMaterialId, materialId);
        return materialFileMapper.selectOne(wrapper);
    }

    @Override
    public List<DataMaterialFileEntity> selectByMaterialIdSet(Set<String> materialIdSet) {
        Wrapper<DataMaterialFileEntity> wrapper = new LambdaQueryWrapper<DataMaterialFileEntity>()
                .in(DataMaterialFileEntity::getMaterialId, materialIdSet);
        return materialFileMapper.selectList(wrapper);
    }
}
