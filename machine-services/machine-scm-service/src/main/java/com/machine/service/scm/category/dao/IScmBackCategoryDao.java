package com.machine.service.scm.category.dao;

import com.machine.service.scm.category.dao.mapper.entity.ScmBackCategoryEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IScmBackCategoryDao {

    String insert(ScmBackCategoryEntity entity);

    int deleteById(String id);

    int update(ScmBackCategoryEntity entity);

    int updateParentId(String id,
                       String parentId);

    long countByIdSet(Collection<String> idCollection);

    long countByNameAndParentId(String parentId,
                                String name);

    ScmBackCategoryEntity getById(String id);

    ScmBackCategoryEntity getByNameAndParentId(String parentId,
                                               String name);

    List<ScmBackCategoryEntity> listAll();

}