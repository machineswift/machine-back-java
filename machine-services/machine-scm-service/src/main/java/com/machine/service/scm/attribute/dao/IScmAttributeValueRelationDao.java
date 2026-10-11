package com.machine.service.scm.attribute.dao;

import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeValueRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmAttributeValueRelationDao {

    String insert(ScmAttributeValueRelationEntity entity);

    int deleteById(String id);

    int deleteByParentValueId(String parentValueId);

    int deleteByParentValueIdSet(Set<String> parentValueIdSet);

    int deleteByChildAttributeId(String childAttributeId);

    int update(ScmAttributeValueRelationEntity entity);

    int countByParentValueId(String parentValueId);

    int countByParentValueAndChildAttribute(String parentValueId, String childAttributeId);

    ScmAttributeValueRelationEntity getById(String id);

    List<String> listChildAttributeIdByParentValueId(String parentValueId);

    List<ScmAttributeValueRelationEntity> listByParentValueIdSet(Set<String> parentValueIdSet);
}