package com.machine.service.scm.property.dao;

import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyGroupEntity;

import java.util.List;
import java.util.Set;

public interface IScmPropertyGroupDao {

    String insert(ScmPropertyGroupEntity entity);

    int update(ScmPropertyGroupEntity entity);

    int deleteById(String id);

    ScmPropertyGroupEntity getById(String id);

    List<ScmPropertyGroupEntity> listByBackCategoryId(String backCategoryId);

    List<ScmPropertyGroupEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet);

    int deleteByBackCategoryId(String backCategoryId);

    int countByBackCategoryAndName(String backCategoryId, String name);
}