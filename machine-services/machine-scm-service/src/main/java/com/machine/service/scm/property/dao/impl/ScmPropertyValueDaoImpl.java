package com.machine.service.scm.property.dao.impl;

import com.machine.service.scm.property.dao.IScmPropertyValueDao;
import com.machine.service.scm.property.dao.mapper.ScmPropertyValueMapper;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmPropertyValueDaoImpl implements IScmPropertyValueDao {

    @Autowired
    private ScmPropertyValueMapper propertyValueMapper;

    @Override
    public String insert(ScmPropertyValueEntity entity) {
        propertyValueMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(ScmPropertyValueEntity entity) {
        return propertyValueMapper.updateById(entity);
    }

    @Override
    public int deleteById(String id) {
        return propertyValueMapper.deleteById(id);
    }

    @Override
    public ScmPropertyValueEntity getById(String id) {
        return propertyValueMapper.selectById(id);
    }

    @Override
    public List<ScmPropertyValueEntity> listByPropertyId(String propertyId) {
        return propertyValueMapper.listByPropertyId(propertyId);
    }

    @Override
    public List<ScmPropertyValueEntity> listByPropertyIdSet(Set<String> propertyIdSet) {
        return propertyValueMapper.listByPropertyIdSet(propertyIdSet);
    }

    @Override
    public int deleteByPropertyId(String propertyId) {
        return propertyValueMapper.deleteByPropertyId(propertyId);
    }

    @Override
    public int countByPropertyIdAndValue(String propertyId, String value) {
        return propertyValueMapper.countByPropertyIdAndValue(propertyId, value);
    }

    @Override
    public int countByPropertyIdAndValueExcludeId(String propertyId, String value, String excludeId) {
        return propertyValueMapper.countByPropertyIdAndValueExcludeId(propertyId, value, excludeId);
    }
}