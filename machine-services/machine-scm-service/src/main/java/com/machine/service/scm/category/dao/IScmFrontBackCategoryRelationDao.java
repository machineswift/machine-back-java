package com.machine.service.scm.category.dao;

import com.machine.service.scm.category.dao.mapper.entity.ScmFrontBackCategoryRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmFrontBackCategoryRelationDao {

    String insert(ScmFrontBackCategoryRelationEntity entity);

    void batchInsert(List<ScmFrontBackCategoryRelationEntity> entityList);

    int deleteById(String id);

    int deleteByFrontCategoryId(String frontCategoryId);

    int update(ScmFrontBackCategoryRelationEntity entity);

    long countByBackCategoryIdSet(Set<String> backCategoryIdSet);

    ScmFrontBackCategoryRelationEntity getById(String id);

    List<ScmFrontBackCategoryRelationEntity> listAll();

    List<ScmFrontBackCategoryRelationEntity> selectByFrontCategoryId(String frontCategoryId);

}