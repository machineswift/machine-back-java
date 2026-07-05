package com.machine.service.scm.category.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.scm.category.dao.IScmFrontBackCategoryRelationDao;
import com.machine.service.scm.category.dao.mapper.ScmFrontBackCategoryRelationMapper;
import com.machine.service.scm.category.dao.mapper.entity.ScmFrontBackCategoryRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmFrontBackCategoryRelationDaoImpl implements IScmFrontBackCategoryRelationDao {

    @Autowired
    private ScmFrontBackCategoryRelationMapper relationMapper;

    @Override
    public String insert(ScmFrontBackCategoryRelationEntity entity) {
        relationMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void batchInsert(List<ScmFrontBackCategoryRelationEntity> entityList) {
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
        Wrapper<ScmFrontBackCategoryRelationEntity> wrapper = new LambdaQueryWrapper<ScmFrontBackCategoryRelationEntity>()
                .eq(ScmFrontBackCategoryRelationEntity::getFrontCategoryId, frontCategoryId);
        return relationMapper.delete(wrapper);
    }

    @Override
    public int update(ScmFrontBackCategoryRelationEntity entity) {
        return relationMapper.updateById(entity);
    }

    @Override
    public long countByBackCategoryIdSet(Set<String> backCategoryIdSet) {
        if (CollectionUtil.isEmpty(backCategoryIdSet)) {
            return 0L;
        }

        Wrapper<ScmFrontBackCategoryRelationEntity> wrapper = new LambdaQueryWrapper<ScmFrontBackCategoryRelationEntity>()
                .in(ScmFrontBackCategoryRelationEntity::getBackCategoryId, backCategoryIdSet);
        return relationMapper.selectCount(wrapper);
    }

    @Override
    public ScmFrontBackCategoryRelationEntity getById(String id) {
        return relationMapper.selectById(id);
    }

    @Override
    public List<ScmFrontBackCategoryRelationEntity> listAll() {
        return relationMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public List<ScmFrontBackCategoryRelationEntity> selectByFrontCategoryId(String frontCategoryId) {
        if (StrUtil.isBlank(frontCategoryId)) {
            return List.of();
        }

        Wrapper<ScmFrontBackCategoryRelationEntity> wrapper = new LambdaQueryWrapper<ScmFrontBackCategoryRelationEntity>()
                .eq(ScmFrontBackCategoryRelationEntity::getFrontCategoryId, frontCategoryId);
        return relationMapper.selectList(wrapper);
    }

}