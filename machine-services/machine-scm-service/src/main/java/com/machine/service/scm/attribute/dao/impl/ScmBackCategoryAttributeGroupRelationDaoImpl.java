package com.machine.service.scm.attribute.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.scm.attribute.dao.IScmBackCategoryAttributeGroupRelationDao;
import com.machine.service.scm.attribute.dao.mapper.ScmBackCategoryAttributeGroupRelationMapper;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmBackCategoryAttributeGroupRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ScmBackCategoryAttributeGroupRelationDaoImpl implements IScmBackCategoryAttributeGroupRelationDao {

    @Autowired
    private ScmBackCategoryAttributeGroupRelationMapper relationMapper;

    @Override
    public void batchInsert(List<ScmBackCategoryAttributeGroupRelationEntity> entityList) {
        if (CollectionUtil.isEmpty(entityList)) {
            return;
        }
        relationMapper.insert(entityList);
    }

    @Override
    public int deleteByGroupId(String groupId) {
        if (StrUtil.isBlank(groupId)) {
            return 0;
        }
        Wrapper<ScmBackCategoryAttributeGroupRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeGroupRelationEntity>()
                        .eq(ScmBackCategoryAttributeGroupRelationEntity::getGroupId, groupId);
        return relationMapper.delete(wrapper);
    }

    @Override
    public int deleteByBackCategoryId(String backCategoryId) {
        if (StrUtil.isBlank(backCategoryId)) {
            return 0;
        }
        Wrapper<ScmBackCategoryAttributeGroupRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeGroupRelationEntity>()
                        .eq(ScmBackCategoryAttributeGroupRelationEntity::getBackCategoryId, backCategoryId);
        return relationMapper.delete(wrapper);
    }

    @Override
    public long countByGroupId(String groupId) {
        if (StrUtil.isBlank(groupId)) {
            return 0L;
        }
        Wrapper<ScmBackCategoryAttributeGroupRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeGroupRelationEntity>()
                        .eq(ScmBackCategoryAttributeGroupRelationEntity::getGroupId, groupId);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public long countByBackCategoryIdSet(Set<String> backCategoryIdSet) {
        if (CollectionUtil.isEmpty(backCategoryIdSet)) {
            return 0L;
        }
        Wrapper<ScmBackCategoryAttributeGroupRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeGroupRelationEntity>()
                        .in(ScmBackCategoryAttributeGroupRelationEntity::getBackCategoryId, backCategoryIdSet);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public List<ScmBackCategoryAttributeGroupRelationEntity> listByGroupId(String groupId) {
        if (StrUtil.isBlank(groupId)) {
            return List.of();
        }
        Wrapper<ScmBackCategoryAttributeGroupRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeGroupRelationEntity>()
                        .eq(ScmBackCategoryAttributeGroupRelationEntity::getGroupId, groupId)
                        .orderByDesc(ScmBackCategoryAttributeGroupRelationEntity::getSort)
                        .orderByAsc(ScmBackCategoryAttributeGroupRelationEntity::getBackCategoryId);
        return relationMapper.selectList(wrapper);
    }

    @Override
    public List<ScmBackCategoryAttributeGroupRelationEntity> listByGroupIdSet(Set<String> groupIdSet) {
        if (CollectionUtil.isEmpty(groupIdSet)) {
            return List.of();
        }
        Wrapper<ScmBackCategoryAttributeGroupRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeGroupRelationEntity>()
                        .in(ScmBackCategoryAttributeGroupRelationEntity::getGroupId, groupIdSet);
        return relationMapper.selectList(wrapper);
    }

    @Override
    public List<ScmBackCategoryAttributeGroupRelationEntity> listByBackCategoryId(String backCategoryId) {
        if (StrUtil.isBlank(backCategoryId)) {
            return List.of();
        }
        Wrapper<ScmBackCategoryAttributeGroupRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeGroupRelationEntity>()
                        .eq(ScmBackCategoryAttributeGroupRelationEntity::getBackCategoryId, backCategoryId)
                        .orderByDesc(ScmBackCategoryAttributeGroupRelationEntity::getSort);
        return relationMapper.selectList(wrapper);
    }

    @Override
    public List<ScmBackCategoryAttributeGroupRelationEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet) {
        if (CollectionUtil.isEmpty(backCategoryIdSet)) {
            return List.of();
        }
        Wrapper<ScmBackCategoryAttributeGroupRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeGroupRelationEntity>()
                        .in(ScmBackCategoryAttributeGroupRelationEntity::getBackCategoryId, backCategoryIdSet);
        return relationMapper.selectList(wrapper);
    }

    @Override
    public List<String> listBackCategoryIdByGroupId(String groupId) {
        if (StrUtil.isBlank(groupId)) {
            return List.of();
        }
        Wrapper<ScmBackCategoryAttributeGroupRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeGroupRelationEntity>()
                        .eq(ScmBackCategoryAttributeGroupRelationEntity::getGroupId, groupId);
        return relationMapper.selectList(wrapper).stream()
                .map(ScmBackCategoryAttributeGroupRelationEntity::getBackCategoryId)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listGroupIdByBackCategoryIdSet(Set<String> backCategoryIdSet) {
        if (CollectionUtil.isEmpty(backCategoryIdSet)) {
            return List.of();
        }
        Wrapper<ScmBackCategoryAttributeGroupRelationEntity> wrapper =
                new LambdaQueryWrapper<ScmBackCategoryAttributeGroupRelationEntity>()
                        .in(ScmBackCategoryAttributeGroupRelationEntity::getBackCategoryId, backCategoryIdSet);
        return relationMapper.selectList(wrapper).stream()
                .map(ScmBackCategoryAttributeGroupRelationEntity::getGroupId)
                .distinct()
                .collect(Collectors.toList());
    }
}
