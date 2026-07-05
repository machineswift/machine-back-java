package com.machine.service.scm.property.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.scm.property.dao.IScmPropertyGroupRelationDao;
import com.machine.service.scm.property.dao.mapper.ScmPropertyGroupRelationMapper;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyGroupRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmPropertyGroupRelationDaoImpl implements IScmPropertyGroupRelationDao {

    @Autowired
    private ScmPropertyGroupRelationMapper relationMapper;

    @Override
    public String insert(ScmPropertyGroupRelationEntity entity) {
        relationMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(ScmPropertyGroupRelationEntity entity) {
        return relationMapper.updateById(entity);
    }

    @Override
    public int deleteById(String id) {
        return relationMapper.deleteById(id);
    }

    @Override
    public ScmPropertyGroupRelationEntity getById(String id) {
        return relationMapper.selectById(id);
    }

    @Override
    public List<String> listPropertyIdByGroupId(String groupId) {
        return relationMapper.listPropertyIdByGroupId(groupId);
    }

    @Override
    public List<ScmPropertyGroupRelationEntity> listByGroupIdSet(Set<String> groupIdSet) {
        return relationMapper.listByGroupIdSet(groupIdSet);
    }

    @Override
    public int deleteByGroupId(String groupId) {
        return relationMapper.deleteByGroupId(groupId);
    }

    @Override
    public int deleteByPropertyId(String propertyId) {
        return relationMapper.deleteByPropertyId(propertyId);
    }

    @Override
    public int countByGroupAndProperty(String groupId, String propertyId) {
        return relationMapper.countByGroupAndProperty(groupId, propertyId);
    }

    @Override
    public List<ScmPropertyGroupRelationEntity> listByGroupId(String groupId) {
        if (StrUtil.isBlank(groupId)) {
            return List.of();
        }
        Wrapper<ScmPropertyGroupRelationEntity> wrapper = new LambdaQueryWrapper<ScmPropertyGroupRelationEntity>()
                .eq(ScmPropertyGroupRelationEntity::getGroupId, groupId)
                .orderByDesc(ScmPropertyGroupRelationEntity::getSort);
        return relationMapper.selectList(wrapper);
    }

    @Override
    public long countByPropertyId(String propertyId) {
        if (StrUtil.isBlank(propertyId)) {
            return 0L;
        }
        Wrapper<ScmPropertyGroupRelationEntity> wrapper = new LambdaQueryWrapper<ScmPropertyGroupRelationEntity>()
                .eq(ScmPropertyGroupRelationEntity::getPropertyId, propertyId);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public void batchInsert(List<ScmPropertyGroupRelationEntity> entityList) {
        if (CollectionUtil.isEmpty(entityList)) {
            return;
        }
        relationMapper.insert(entityList);
    }
}