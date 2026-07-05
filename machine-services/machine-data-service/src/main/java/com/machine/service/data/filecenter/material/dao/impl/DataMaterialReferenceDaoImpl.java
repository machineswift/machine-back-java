package com.machine.service.data.filecenter.material.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.filecenter.material.dao.IDataMaterialReferenceDao;
import com.machine.service.data.filecenter.material.dao.mapper.DataMaterialReferenceMapper;
import com.machine.service.data.filecenter.material.dao.mapper.entity.DataMaterialReferenceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataMaterialReferenceDaoImpl implements IDataMaterialReferenceDao {

    @Autowired
    private DataMaterialReferenceMapper materialReferenceMapper;

    @Override
    public String insert(DataMaterialReferenceEntity entity) {
        materialReferenceMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int delete(String id) {
        if (StrUtil.isBlank(id)) {
            return 0;
        }
        return materialReferenceMapper.deleteById(id);
    }

    @Override
    public int deleteByMaterialId(String materialId) {
        if (StrUtil.isBlank(materialId)) {
            return 0;
        }
        Wrapper<DataMaterialReferenceEntity> wrapper = new LambdaQueryWrapper<DataMaterialReferenceEntity>()
                .eq(DataMaterialReferenceEntity::getMaterialId, materialId);
        return materialReferenceMapper.delete(wrapper);
    }

    @Override
    public DataMaterialReferenceEntity getById(String id) {
        if (StrUtil.isBlank(id)) {
            return null;
        }
        return materialReferenceMapper.selectById(id);
    }

    @Override
    public DataMaterialReferenceEntity getByMaterialIdAndEntity(String materialId,
                                                                String entity,
                                                                String entityId) {
        if (StrUtil.isBlank(materialId) || StrUtil.isBlank(entity) || StrUtil.isBlank(entityId)) {
            return null;
        }
        Wrapper<DataMaterialReferenceEntity> wrapper = new LambdaQueryWrapper<DataMaterialReferenceEntity>()
                .eq(DataMaterialReferenceEntity::getMaterialId, materialId)
                .eq(DataMaterialReferenceEntity::getEntity, entity)
                .eq(DataMaterialReferenceEntity::getEntityId, entityId);
        return materialReferenceMapper.selectOne(wrapper);
    }

    @Override
    public List<DataMaterialReferenceEntity> listByMaterialId(String materialId) {
        if (StrUtil.isBlank(materialId)) {
            return List.of();
        }
        Wrapper<DataMaterialReferenceEntity> wrapper = new LambdaQueryWrapper<DataMaterialReferenceEntity>()
                .eq(DataMaterialReferenceEntity::getMaterialId, materialId);
        return materialReferenceMapper.selectList(wrapper);
    }

    @Override
    public List<DataMaterialReferenceEntity> listByMaterialIdSet(Set<String> materialIdSet) {
        if (CollectionUtil.isEmpty(materialIdSet)) {
            return List.of();
        }
        Wrapper<DataMaterialReferenceEntity> wrapper = new LambdaQueryWrapper<DataMaterialReferenceEntity>()
                .in(DataMaterialReferenceEntity::getMaterialId, materialIdSet);
        return materialReferenceMapper.selectList(wrapper);
    }

    @Override
    public List<DataMaterialReferenceEntity> listByEntity(String entity,
                                                          String entityId) {
        if (StrUtil.isBlank(entity) || StrUtil.isBlank(entityId)) {
            return List.of();
        }
        Wrapper<DataMaterialReferenceEntity> wrapper = new LambdaQueryWrapper<DataMaterialReferenceEntity>()
                .eq(DataMaterialReferenceEntity::getEntity, entity)
                .eq(DataMaterialReferenceEntity::getEntityId, entityId);
        return materialReferenceMapper.selectList(wrapper);
    }

    @Override
    public Long selectCountByEntity(String entity,
                                    String entityId) {
        if (StrUtil.isBlank(entity) || StrUtil.isBlank(entityId)) {
            return 0L;
        }
        Wrapper<DataMaterialReferenceEntity> wrapper = new LambdaQueryWrapper<DataMaterialReferenceEntity>()
                .eq(DataMaterialReferenceEntity::getEntity, entity)
                .eq(DataMaterialReferenceEntity::getEntityId, entityId);
        return materialReferenceMapper.selectCount(wrapper);
    }
}
