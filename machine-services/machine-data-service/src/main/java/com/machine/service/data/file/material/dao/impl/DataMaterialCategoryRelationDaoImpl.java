package com.machine.service.data.file.material.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.file.material.dao.IDataMaterialCategoryRelationDao;
import com.machine.service.data.file.material.dao.mapper.DataMaterialCategoryRelationMapper;
import com.machine.service.data.file.material.dao.mapper.entity.DataMaterialCategoryRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataMaterialCategoryRelationDaoImpl implements IDataMaterialCategoryRelationDao {

    @Autowired
    private DataMaterialCategoryRelationMapper materialCategoryRelationMapper;

    @Override
    public Long selectCountByCategoryId(String categoryId) {
        if (StrUtil.isBlank(categoryId)) {
            return 0L;
        }
        Wrapper<DataMaterialCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataMaterialCategoryRelationEntity>()
                .eq(DataMaterialCategoryRelationEntity::getCategoryId, categoryId);
        return materialCategoryRelationMapper.selectCount(queryWrapper);
    }

    @Override
    public Long selectCountByCategoryIdSet(Set<String> categoryIdSet) {
        if (CollectionUtil.isEmpty(categoryIdSet)) {
            return 0L;
        }
        Wrapper<DataMaterialCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataMaterialCategoryRelationEntity>()
                .in(DataMaterialCategoryRelationEntity::getCategoryId, categoryIdSet);
        return materialCategoryRelationMapper.selectCount(queryWrapper);
    }

    @Override
    public void insert(DataMaterialCategoryRelationEntity entity) {
        materialCategoryRelationMapper.insert(entity);
    }

    @Override
    public void deleteByMaterialId(String materialId) {
        if (StrUtil.isBlank(materialId)) {
            return;
        }
        Wrapper<DataMaterialCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataMaterialCategoryRelationEntity>()
                .eq(DataMaterialCategoryRelationEntity::getMaterialId, materialId);
        materialCategoryRelationMapper.delete(queryWrapper);
    }

    @Override
    public List<DataMaterialCategoryRelationEntity> listByMaterialId(String materialId) {
        if (StrUtil.isBlank(materialId)) {
            return List.of();
        }
        Wrapper<DataMaterialCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataMaterialCategoryRelationEntity>()
                .eq(DataMaterialCategoryRelationEntity::getCategoryId, materialId);
        return materialCategoryRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<DataMaterialCategoryRelationEntity> listByCategoryIdSet(Set<String> categoryIdSet) {
        if (CollectionUtil.isEmpty(categoryIdSet)) {
            return List.of();
        }

        Wrapper<DataMaterialCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataMaterialCategoryRelationEntity>()
                .in(DataMaterialCategoryRelationEntity::getCategoryId, categoryIdSet);
        return materialCategoryRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<DataMaterialCategoryRelationEntity> listByMaterialIdSet(Set<String> materialIdSet) {
        if (CollectionUtil.isEmpty(materialIdSet)) {
            return List.of();
        }

        Wrapper<DataMaterialCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataMaterialCategoryRelationEntity>()
                .in(DataMaterialCategoryRelationEntity::getMaterialId, materialIdSet);
        return materialCategoryRelationMapper.selectList(queryWrapper);
    }
}
