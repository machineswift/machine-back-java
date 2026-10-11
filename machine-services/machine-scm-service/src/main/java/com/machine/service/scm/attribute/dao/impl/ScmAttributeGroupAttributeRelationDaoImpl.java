package com.machine.service.scm.attribute.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.scm.attribute.dao.IScmAttributeGroupAttributeRelationDao;
import com.machine.service.scm.attribute.dao.mapper.ScmAttributeGroupAttributeRelationMapper;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupAttributeRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmAttributeGroupAttributeRelationDaoImpl implements IScmAttributeGroupAttributeRelationDao {

    @Autowired
    private ScmAttributeGroupAttributeRelationMapper relationMapper;

    @Override
    public String insert(ScmAttributeGroupAttributeRelationEntity entity) {
        relationMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void batchInsert(List<ScmAttributeGroupAttributeRelationEntity> entityList) {
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
    public int deleteByGroupId(String groupId) {
        return relationMapper.deleteByGroupId(groupId);
    }

    @Override
    public int deleteByAttributeId(String attributeId) {
        return relationMapper.deleteByAttributeId(attributeId);
    }

    @Override
    public int update(ScmAttributeGroupAttributeRelationEntity entity) {
        return relationMapper.updateById(entity);
    }

    @Override
    public int countByGroupAndAttribute(String groupId, String attributeId) {
        return relationMapper.countByGroupAndAttribute(groupId, attributeId);
    }

    @Override
    public long countByGroupId(String groupId) {
        if (StrUtil.isBlank(groupId)) {
            return 0L;
        }
        Wrapper<ScmAttributeGroupAttributeRelationEntity> wrapper = new LambdaQueryWrapper<ScmAttributeGroupAttributeRelationEntity>()
                .eq(ScmAttributeGroupAttributeRelationEntity::getGroupId, groupId);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public long countByAttributeId(String attributeId) {
        if (StrUtil.isBlank(attributeId)) {
            return 0L;
        }
        Wrapper<ScmAttributeGroupAttributeRelationEntity> wrapper = new LambdaQueryWrapper<ScmAttributeGroupAttributeRelationEntity>()
                .eq(ScmAttributeGroupAttributeRelationEntity::getAttributeId, attributeId);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public ScmAttributeGroupAttributeRelationEntity getById(String id) {
        return relationMapper.selectById(id);
    }

    @Override
    public List<ScmAttributeGroupAttributeRelationEntity> listByGroupId(String groupId) {
        if (StrUtil.isBlank(groupId)) {
            return List.of();
        }
        Wrapper<ScmAttributeGroupAttributeRelationEntity> wrapper = new LambdaQueryWrapper<ScmAttributeGroupAttributeRelationEntity>()
                .eq(ScmAttributeGroupAttributeRelationEntity::getGroupId, groupId)
                .orderByDesc(ScmAttributeGroupAttributeRelationEntity::getSort)
                .orderByAsc(ScmAttributeGroupAttributeRelationEntity::getAttributeId);
        return relationMapper.selectList(wrapper);
    }

    @Override
    public List<ScmAttributeGroupAttributeRelationEntity> listByGroupIdSet(Set<String> groupIdSet) {
        return relationMapper.listByGroupIdSet(groupIdSet);
    }

    @Override
    public List<ScmAttributeGroupAttributeRelationEntity> listByAttributeIdSet(Set<String> attributeIdSet) {
        return relationMapper.listByAttributeIdSet(attributeIdSet);
    }

    @Override
    public List<ScmAttributeGroupAttributeRelationEntity> listByAttributeId(String attributeId) {
        if (StrUtil.isBlank(attributeId)) {
            return List.of();
        }
        Wrapper<ScmAttributeGroupAttributeRelationEntity> wrapper = new LambdaQueryWrapper<ScmAttributeGroupAttributeRelationEntity>()
                .eq(ScmAttributeGroupAttributeRelationEntity::getAttributeId, attributeId)
                .orderByDesc(ScmAttributeGroupAttributeRelationEntity::getSort)
                .orderByAsc(ScmAttributeGroupAttributeRelationEntity::getGroupId);
        return relationMapper.selectList(wrapper);
    }

    @Override
    public List<String> listAttributeIdByGroupId(String groupId) {
        return relationMapper.listAttributeIdByGroupId(groupId);
    }
}