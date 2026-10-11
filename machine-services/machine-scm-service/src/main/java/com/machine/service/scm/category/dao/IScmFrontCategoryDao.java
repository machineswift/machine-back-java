package com.machine.service.scm.category.dao;

import com.machine.service.scm.category.dao.mapper.entity.ScmFrontCategoryEntity;

import java.util.List;

public interface IScmFrontCategoryDao {

    String insert(ScmFrontCategoryEntity entity);

    int deleteById(String id);

    int update(ScmFrontCategoryEntity entity);

    int updateParentId(String id,
                       String parentId);

    long countByParentIdAndName(String parentId,
                                String name);

    ScmFrontCategoryEntity getById(String id);

    ScmFrontCategoryEntity getByParentIdAndName(String parentId,
                                                String name);

    List<ScmFrontCategoryEntity> listAll();
}