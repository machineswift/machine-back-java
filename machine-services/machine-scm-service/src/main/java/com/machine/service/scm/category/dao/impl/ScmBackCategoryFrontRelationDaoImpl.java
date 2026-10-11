package com.machine.service.scm.category.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.scm.category.dao.IScmBackCategoryFrontRelationDao;
import com.machine.service.scm.category.dao.mapper.ScmBackCategoryFrontRelationMapper;
import com.machine.service.scm.category.dao.mapper.entity.ScmBackCategoryFrontRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmBackCategoryFrontRelationDaoImpl implements IScmBackCategoryFrontRelationDao {

    @Autowired
    private ScmBackCategoryFrontRelationMapper relationMapper;

    @Override
    public String insert(ScmBackCategoryFrontRelationEntity entity) {
        relationMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void batchInsert(List<ScmBackCategoryFrontRelationEntity> entityList) {
        relationMapper.insert(entityList);
    }

    @Override
    public int deleteById(String id) {
        return relationMapper.deleteById(id);
    }

    @Override
    public int deleteByFrontCategoryId(String frontCategoryId) {
        if (StrUtil.isBlank(frontCategoryId)) {
            return 0;
        }
        Wrapper<ScmBackCategoryFrontRelationEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryFrontRelationEntity>()
                .eq(ScmBackCategoryFrontRelationEntity::getFrontCategoryId, frontCategoryId);
        return relationMapper.delete(wrapper);
    }

    @Override
    public int update(ScmBackCategoryFrontRelationEntity entity) {
        return relationMapper.updateById(entity);
    }

    @Override
    public long countByBackCategoryIdSet(Set<String> backCategoryIdSet) {
        if (CollectionUtil.isEmpty(backCategoryIdSet)) {
            return 0L;
        }

        Wrapper<ScmBackCategoryFrontRelationEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryFrontRelationEntity>()
                .in(ScmBackCategoryFrontRelationEntity::getBackCategoryId, backCategoryIdSet);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public long countByFrontCategoryIdSet(Set<String> frontCategoryIdSet) {
        if (CollectionUtil.isEmpty(frontCategoryIdSet)) {
            return 0L;
        }

        Wrapper<ScmBackCategoryFrontRelationEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryFrontRelationEntity>()
                .in(ScmBackCategoryFrontRelationEntity::getFrontCategoryId, frontCategoryIdSet);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public ScmBackCategoryFrontRelationEntity getById(String id) {
        return relationMapper.selectById(id);
    }

    @Override
    public List<ScmBackCategoryFrontRelationEntity> listAll() {
        return relationMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public List<ScmBackCategoryFrontRelationEntity> selectByFrontCategoryId(String frontCategoryId) {
        if (StrUtil.isBlank(frontCategoryId)) {
            return List.of();
        }

        Wrapper<ScmBackCategoryFrontRelationEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryFrontRelationEntity>()
                .eq(ScmBackCategoryFrontRelationEntity::getFrontCategoryId, frontCategoryId);
        return relationMapper.selectList(wrapper);
    }

    @Override
    public List<ScmBackCategoryFrontRelationEntity> selectByBackCategoryId(String backCategoryId) {
        if (StrUtil.isBlank(backCategoryId)) {
            return List.of();
        }

        Wrapper<ScmBackCategoryFrontRelationEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryFrontRelationEntity>()
                .eq(ScmBackCategoryFrontRelationEntity::getBackCategoryId, backCategoryId);
        return relationMapper.selectList(wrapper);
    }

}