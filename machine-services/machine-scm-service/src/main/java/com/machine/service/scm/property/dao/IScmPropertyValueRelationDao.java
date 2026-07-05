package com.machine.service.scm.property.dao;

import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyValueRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmPropertyValueRelationDao {

    String insert(ScmPropertyValueRelationEntity entity);

    int update(ScmPropertyValueRelationEntity entity);

    int deleteById(String id);

    ScmPropertyValueRelationEntity getById(String id);

    List<String> listChildPropertyIdByParentValueId(String parentValueId);

    List<ScmPropertyValueRelationEntity> listByParentValueIdSet(Set<String> parentValueIdSet);

    int deleteByParentValueId(String parentValueId);

    int deleteByChildPropertyId(String childPropertyId);

    int countByParentValueAndChildProperty(String parentValueId, String childPropertyId);
}