package com.machine.service.data.brand.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.brand.dao.IDataBrandCategoryRelationDao;
import com.machine.service.data.brand.dao.mapper.DataBrandCategoryRelationMapper;
import com.machine.service.data.brand.dao.mapper.entity.DataBrandCategoryRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataBrandCategoryRelationDaoImpl implements IDataBrandCategoryRelationDao {

    @Autowired
    private DataBrandCategoryRelationMapper relationMapper;

    @Override
    public void insert(DataBrandCategoryRelationEntity entity) {
        relationMapper.insert(entity);
    }

    @Override
    public void deleteByBrandId(String brandId) {
        if (StrUtil.isBlank(brandId)) {
            return;
        }
        Wrapper<DataBrandCategoryRelationEntity> wrapper = new LambdaQueryWrapper<DataBrandCategoryRelationEntity>()
                .eq(DataBrandCategoryRelationEntity::getBrandId, brandId);
        relationMapper.delete(wrapper);
    }

    @Override
    public Long selectCountByCategoryId(String categoryId) {
        if (StrUtil.isBlank(categoryId)) {
            return 0L;
        }
        Wrapper<DataBrandCategoryRelationEntity> wrapper = new LambdaQueryWrapper<DataBrandCategoryRelationEntity>()
                .eq(DataBrandCategoryRelationEntity::getCategoryId, categoryId);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public Long selectCountByCategoryIdSet(Set<String> categoryIdSet) {
        if (CollectionUtil.isEmpty(categoryIdSet)) {
            return 0L;
        }
        Wrapper<DataBrandCategoryRelationEntity> wrapper = new LambdaQueryWrapper<DataBrandCategoryRelationEntity>()
                .in(DataBrandCategoryRelationEntity::getCategoryId, categoryIdSet);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public List<DataBrandCategoryRelationEntity> listByBrandId(String brandId) {
        if (StrUtil.isBlank(brandId)) {
            return List.of();
        }
        Wrapper<DataBrandCategoryRelationEntity> wrapper = new LambdaQueryWrapper<DataBrandCategoryRelationEntity>()
                .eq(DataBrandCategoryRelationEntity::getBrandId, brandId);
        return relationMapper.selectList(wrapper);
    }

    @Override
    public List<DataBrandCategoryRelationEntity> listByBrandIdSet(Set<String> brandIdSet) {
        if (CollectionUtil.isEmpty(brandIdSet)) {
            return List.of();
        }
        Wrapper<DataBrandCategoryRelationEntity> wrapper = new LambdaQueryWrapper<DataBrandCategoryRelationEntity>()
                .in(DataBrandCategoryRelationEntity::getBrandId, brandIdSet);
        return relationMapper.selectList(wrapper);
    }
}
