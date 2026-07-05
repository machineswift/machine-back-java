package com.machine.service.scm.property.dao;

import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyGroupRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmPropertyGroupRelationDao {

    String insert(ScmPropertyGroupRelationEntity entity);

    int update(ScmPropertyGroupRelationEntity entity);

    int deleteById(String id);

    ScmPropertyGroupRelationEntity getById(String id);

    List<String> listPropertyIdByGroupId(String groupId);

    List<ScmPropertyGroupRelationEntity> listByGroupIdSet(Set<String> groupIdSet);

    int deleteByGroupId(String groupId);

    int deleteByPropertyId(String propertyId);

    int countByGroupAndProperty(String groupId, String propertyId);

    List<ScmPropertyGroupRelationEntity> listByGroupId(String groupId);

    long countByPropertyId(String propertyId);

    void batchInsert(List<ScmPropertyGroupRelationEntity> entityList);
}