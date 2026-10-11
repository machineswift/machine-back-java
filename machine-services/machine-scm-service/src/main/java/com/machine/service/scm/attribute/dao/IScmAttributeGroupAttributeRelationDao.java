package com.machine.service.scm.attribute.dao;

import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupAttributeRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmAttributeGroupAttributeRelationDao {

    String insert(ScmAttributeGroupAttributeRelationEntity entity);

    void batchInsert(List<ScmAttributeGroupAttributeRelationEntity> entityList);

    int deleteById(String id);

    int deleteByGroupId(String groupId);

    int deleteByAttributeId(String attributeId);

    int update(ScmAttributeGroupAttributeRelationEntity entity);

    int countByGroupAndAttribute(String groupId, String attributeId);

    long countByGroupId(String groupId);

    long countByAttributeId(String attributeId);

    ScmAttributeGroupAttributeRelationEntity getById(String id);

    List<ScmAttributeGroupAttributeRelationEntity> listByGroupId(String groupId);

    List<ScmAttributeGroupAttributeRelationEntity> listByGroupIdSet(Set<String> groupIdSet);

    List<ScmAttributeGroupAttributeRelationEntity> listByAttributeIdSet(Set<String> attributeIdSet);

    List<ScmAttributeGroupAttributeRelationEntity> listByAttributeId(String attributeId);

    List<String> listAttributeIdByGroupId(String groupId);
}