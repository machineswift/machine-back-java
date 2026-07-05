package com.machine.service.scm.property.dao.impl;

import com.machine.service.scm.property.dao.IScmPropertyGroupDao;
import com.machine.service.scm.property.dao.mapper.ScmPropertyGroupMapper;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmPropertyGroupDaoImpl implements IScmPropertyGroupDao {

    @Autowired
    private ScmPropertyGroupMapper propertyGroupMapper;

    @Override
    public String insert(ScmPropertyGroupEntity entity) {
        propertyGroupMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(ScmPropertyGroupEntity entity) {
        return propertyGroupMapper.updateById(entity);
    }

    @Override
    public int deleteById(String id) {
        return propertyGroupMapper.deleteById(id);
    }

    @Override
    public ScmPropertyGroupEntity getById(String id) {
        return propertyGroupMapper.selectById(id);
    }

    @Override
    public List<ScmPropertyGroupEntity> listByBackCategoryId(String backCategoryId) {
        return propertyGroupMapper.listByBackCategoryId(backCategoryId);
    }

    @Override
    public List<ScmPropertyGroupEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet) {
        return propertyGroupMapper.listByBackCategoryIdSet(backCategoryIdSet);
    }

    @Override
    public int deleteByBackCategoryId(String backCategoryId) {
        return propertyGroupMapper.deleteByBackCategoryId(backCategoryId);
    }

    @Override
    public int countByBackCategoryAndName(String backCategoryId, String name) {
        return propertyGroupMapper.countByBackCategoryAndName(backCategoryId, name);
    }
}