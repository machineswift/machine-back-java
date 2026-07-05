package com.machine.service.scm.property.dao.impl;

import com.machine.service.scm.property.dao.IScmPropertyValueRelationDao;
import com.machine.service.scm.property.dao.mapper.ScmPropertyValueRelationMapper;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyValueRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmPropertyValueRelationDaoImpl implements IScmPropertyValueRelationDao {

    @Autowired
    private ScmPropertyValueRelationMapper relationMapper;

    @Override
    public String insert(ScmPropertyValueRelationEntity entity) {
        relationMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(ScmPropertyValueRelationEntity entity) {
        return relationMapper.updateById(entity);
    }

    @Override
    public int deleteById(String id) {
        return relationMapper.deleteById(id);
    }

    @Override
    public ScmPropertyValueRelationEntity getById(String id) {
        return relationMapper.selectById(id);
    }

    @Override
    public List<String> listChildPropertyIdByParentValueId(String parentValueId) {
        return relationMapper.listChildPropertyIdByParentValueId(parentValueId);
    }

    @Override
    public List<ScmPropertyValueRelationEntity> listByParentValueIdSet(Set<String> parentValueIdSet) {
        return relationMapper.listByParentValueIdSet(parentValueIdSet);
    }

    @Override
    public int deleteByParentValueId(String parentValueId) {
        return relationMapper.deleteByParentValueId(parentValueId);
    }

    @Override
    public int deleteByChildPropertyId(String childPropertyId) {
        return relationMapper.deleteByChildPropertyId(childPropertyId);
    }

    @Override
    public int countByParentValueAndChildProperty(String parentValueId, String childPropertyId) {
        return relationMapper.countByParentValueAndChildProperty(parentValueId, childPropertyId);
    }
}