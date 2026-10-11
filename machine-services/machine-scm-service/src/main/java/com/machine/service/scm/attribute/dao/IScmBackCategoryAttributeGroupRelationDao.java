package com.machine.service.scm.attribute.dao;

import com.machine.service.scm.attribute.dao.mapper.entity.ScmBackCategoryAttributeGroupRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmBackCategoryAttributeGroupRelationDao {

    void batchInsert(List<ScmBackCategoryAttributeGroupRelationEntity> entityList);

    int deleteByGroupId(String groupId);

    int deleteByBackCategoryId(String backCategoryId);

    long countByGroupId(String groupId);

    long countByBackCategoryIdSet(Set<String> backCategoryIdSet);

    List<ScmBackCategoryAttributeGroupRelationEntity> listByGroupId(String groupId);

    List<ScmBackCategoryAttributeGroupRelationEntity> listByGroupIdSet(Set<String> groupIdSet);

    List<ScmBackCategoryAttributeGroupRelationEntity> listByBackCategoryId(String backCategoryId);

    List<ScmBackCategoryAttributeGroupRelationEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet);

    List<String> listBackCategoryIdByGroupId(String groupId);

    List<String> listGroupIdByBackCategoryIdSet(Set<String> backCategoryIdSet);
}
