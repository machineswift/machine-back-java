package com.machine.service.scm.attribute.dao;

import com.machine.service.scm.attribute.dao.mapper.entity.ScmBackCategoryAttributeRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmBackCategoryAttributeRelationDao {

    String insert(ScmBackCategoryAttributeRelationEntity entity);

    void batchInsert(List<ScmBackCategoryAttributeRelationEntity> entityList);

    int deleteById(String id);

    int deleteByBackCategoryId(String backCategoryId);

    int deleteByAttributeId(String attributeId);

    int update(ScmBackCategoryAttributeRelationEntity entity);

    long countByAttributeId(String attributeId);

    long countByBackCategoryIdSet(Set<String> backCategoryIdSet);

    ScmBackCategoryAttributeRelationEntity getById(String id);

    List<ScmBackCategoryAttributeRelationEntity> listByAttributeIdSet(Set<String> attributeIdSet);

    List<ScmBackCategoryAttributeRelationEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet);

}