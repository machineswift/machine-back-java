package com.machine.service.scm.property.dao;

import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyValueEntity;

import java.util.List;
import java.util.Set;

public interface IScmPropertyValueDao {

    String insert(ScmPropertyValueEntity entity);

    int update(ScmPropertyValueEntity entity);

    int deleteById(String id);

    ScmPropertyValueEntity getById(String id);

    List<ScmPropertyValueEntity> listByPropertyId(String propertyId);

    List<ScmPropertyValueEntity> listByPropertyIdSet(Set<String> propertyIdSet);

    int deleteByPropertyId(String propertyId);

    int countByPropertyIdAndValue(String propertyId, String value);

    int countByPropertyIdAndValueExcludeId(String propertyId, String value, String excludeId);
}