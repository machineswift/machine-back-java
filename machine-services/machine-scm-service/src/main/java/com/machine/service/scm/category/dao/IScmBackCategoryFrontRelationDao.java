package com.machine.service.scm.category.dao;

import com.machine.service.scm.category.dao.mapper.entity.ScmBackCategoryFrontRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmBackCategoryFrontRelationDao {

    String insert(ScmBackCategoryFrontRelationEntity entity);

    void batchInsert(List<ScmBackCategoryFrontRelationEntity> entityList);

    int deleteById(String id);

    int deleteByFrontCategoryId(String frontCategoryId);

    int update(ScmBackCategoryFrontRelationEntity entity);

    long countByBackCategoryIdSet(Set<String> backCategoryIdSet);

    /**
     * 统计前台分类（含子孙节点）已关联的后台分类数量
     */
    long countByFrontCategoryIdSet(Set<String> frontCategoryIdSet);

    ScmBackCategoryFrontRelationEntity getById(String id);

    List<ScmBackCategoryFrontRelationEntity> listAll();

    List<ScmBackCategoryFrontRelationEntity> selectByFrontCategoryId(String frontCategoryId);

    List<ScmBackCategoryFrontRelationEntity> selectByBackCategoryId(String backCategoryId);

}