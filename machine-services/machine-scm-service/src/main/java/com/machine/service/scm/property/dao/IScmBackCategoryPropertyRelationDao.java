package com.machine.service.scm.property.dao;

import com.machine.service.scm.property.dao.mapper.entity.ScmBackCategoryPropertyRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmBackCategoryPropertyRelationDao {

    String insert(ScmBackCategoryPropertyRelationEntity entity);

    int update(ScmBackCategoryPropertyRelationEntity entity);

    int deleteById(String id);

    ScmBackCategoryPropertyRelationEntity getById(String id);

    List<ScmBackCategoryPropertyRelationEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet);

    long countByPropertyId(String propertyId);

}