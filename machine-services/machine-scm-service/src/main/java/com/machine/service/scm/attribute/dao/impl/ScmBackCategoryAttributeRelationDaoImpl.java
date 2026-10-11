package com.machine.service.scm.attribute.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.scm.attribute.dao.IScmBackCategoryAttributeRelationDao;
import com.machine.service.scm.attribute.dao.mapper.ScmBackCategoryAttributeRelationMapper;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmBackCategoryAttributeRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmBackCategoryAttributeRelationDaoImpl implements IScmBackCategoryAttributeRelationDao {

    @Autowired
    private ScmBackCategoryAttributeRelationMapper relationMapper;

    @Override
    public String insert(ScmBackCategoryAttributeRelationEntity entity) {
        relationMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void batchInsert(List<ScmBackCategoryAttributeRelationEntity> entityList) {
        if (CollectionUtil.isEmpty(entityList)) {
            return;
        }
        relationMapper.insert(entityList);
    }

    @Override
    public int deleteById(String id) {
        return relationMapper.deleteById(id);
    }

    @Override
    public int deleteByBackCategoryId(String backCategoryId) {
        if (StrUtil.isBlank(backCategoryId)) {
            return 0;
        }
        Wrapper<ScmBackCategoryAttributeRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeRelationEntity>()
                        .eq(ScmBackCategoryAttributeRelationEntity::getBackCategoryId, backCategoryId);
        return relationMapper.delete(wrapper);
    }

    @Override
    public int deleteByAttributeId(String attributeId) {
        if (StrUtil.isBlank(attributeId)) {
            return 0;
        }
        Wrapper<ScmBackCategoryAttributeRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeRelationEntity>()
                        .eq(ScmBackCategoryAttributeRelationEntity::getAttributeId, attributeId);
        return relationMapper.delete(wrapper);
    }

    @Override
    public int update(ScmBackCategoryAttributeRelationEntity entity) {
        return relationMapper.updateById(entity);
    }

    @Override
    public long countByAttributeId(String attributeId) {
        if (StrUtil.isBlank(attributeId)) {
            return 0L;
        }
        Wrapper<ScmBackCategoryAttributeRelationEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryAttributeRelationEntity>()
                .eq(ScmBackCategoryAttributeRelationEntity::getAttributeId, attributeId);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public long countByBackCategoryIdSet(Set<String> backCategoryIdSet) {
        if (CollectionUtil.isEmpty(backCategoryIdSet)) {
            return 0L;
        }
        Wrapper<ScmBackCategoryAttributeRelationEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryAttributeRelationEntity>()
                .in(ScmBackCategoryAttributeRelationEntity::getBackCategoryId, backCategoryIdSet);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public ScmBackCategoryAttributeRelationEntity getById(String id) {
        return relationMapper.selectById(id);
    }

    @Override
    public List<ScmBackCategoryAttributeRelationEntity> listByAttributeIdSet(Set<String> attributeIdSet) {
        if (CollectionUtil.isEmpty(attributeIdSet)) {
            return List.of();
        }
        Wrapper<ScmBackCategoryAttributeRelationEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryAttributeRelationEntity>()
                .in(ScmBackCategoryAttributeRelationEntity::getAttributeId, attributeIdSet);
        return relationMapper.selectList(wrapper);
    }

    @Override
    public List<ScmBackCategoryAttributeRelationEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet) {
        if (CollectionUtil.isEmpty(backCategoryIdSet)) {
            return List.of();
        }
        Wrapper<ScmBackCategoryAttributeRelationEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryAttributeRelationEntity>()
                .in(ScmBackCategoryAttributeRelationEntity::getBackCategoryId, backCategoryIdSet);
        return relationMapper.selectList(wrapper);
    }

}