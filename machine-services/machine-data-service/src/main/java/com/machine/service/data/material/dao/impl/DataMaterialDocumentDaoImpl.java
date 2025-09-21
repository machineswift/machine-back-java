package com.machine.service.data.material.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.material.dao.IDataMaterialDocumentDao;
import com.machine.service.data.material.dao.mapper.DataMaterialDocumentMapper;
import com.machine.service.data.material.dao.mapper.entity.DataMaterialDocumentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataMaterialDocumentDaoImpl implements IDataMaterialDocumentDao {

    @Autowired
    private DataMaterialDocumentMapper materialDocumentMapper;

    @Override
    public void insert(DataMaterialDocumentEntity entity) {
        materialDocumentMapper.insert(entity);
    }

    @Override
    public int deleteById(String id) {
        return materialDocumentMapper.deleteById(id);
    }

    @Override
    public int deleteByMaterialId(String materialId) {
        Wrapper<DataMaterialDocumentEntity> wrapper = new LambdaQueryWrapper<DataMaterialDocumentEntity>()
                .eq(DataMaterialDocumentEntity::getMaterialId, materialId);
        return materialDocumentMapper.delete(wrapper);
    }

    @Override
    public DataMaterialDocumentEntity getById(String id) {
        return materialDocumentMapper.selectById(id);
    }

    @Override
    public DataMaterialDocumentEntity getByMaterialId(String materialId) {
        Wrapper<DataMaterialDocumentEntity> wrapper = new LambdaQueryWrapper<DataMaterialDocumentEntity>()
                .eq(DataMaterialDocumentEntity::getMaterialId, materialId);
        return materialDocumentMapper.selectOne(wrapper);
    }

    @Override
    public List<DataMaterialDocumentEntity> selectByMaterialIdSet(Set<String> materialIdSet) {
        Wrapper<DataMaterialDocumentEntity> wrapper = new LambdaQueryWrapper<DataMaterialDocumentEntity>()
                .in(DataMaterialDocumentEntity::getMaterialId, materialIdSet);
        return materialDocumentMapper.selectList(wrapper);
    }
}
