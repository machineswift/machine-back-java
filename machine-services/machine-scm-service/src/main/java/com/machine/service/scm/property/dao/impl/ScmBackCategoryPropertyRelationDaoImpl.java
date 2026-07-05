package com.machine.service.scm.property.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.scm.property.dao.IScmBackCategoryPropertyRelationDao;
import com.machine.service.scm.property.dao.mapper.ScmBackCategoryPropertyRelationMapper;
import com.machine.service.scm.property.dao.mapper.entity.ScmBackCategoryPropertyRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmBackCategoryPropertyRelationDaoImpl implements IScmBackCategoryPropertyRelationDao {

    @Autowired
    private ScmBackCategoryPropertyRelationMapper relationMapper;

    @Override
    public String insert(ScmBackCategoryPropertyRelationEntity entity) {
        relationMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(ScmBackCategoryPropertyRelationEntity entity) {
        return relationMapper.updateById(entity);
    }

    @Override
    public int deleteById(String id) {
        return relationMapper.deleteById(id);
    }

    @Override
    public ScmBackCategoryPropertyRelationEntity getById(String id) {
        return relationMapper.selectById(id);
    }

    @Override
    public List<ScmBackCategoryPropertyRelationEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet) {
        if (CollectionUtil.isEmpty(backCategoryIdSet)) {
            return List.of();
        }
        Wrapper<ScmBackCategoryPropertyRelationEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryPropertyRelationEntity>()
                .in(ScmBackCategoryPropertyRelationEntity::getBackCategoryId, backCategoryIdSet);
        return relationMapper.selectList(wrapper);
    }

    @Override
    public long countByPropertyId(String propertyId) {
        if (StrUtil.isBlank(propertyId)) {
            return 0L;
        }
        Wrapper<ScmBackCategoryPropertyRelationEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryPropertyRelationEntity>()
                .eq(ScmBackCategoryPropertyRelationEntity::getPropertyId, propertyId);
        return relationMapper.selectCount(wrapper);
    }

}