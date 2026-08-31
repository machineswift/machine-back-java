package com.machine.service.iam.biam.user.dao;

import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleRelationEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IBIamUserRoleRelationDao {

    String insert(String userId,
                  String roleId);

    void batchInsert(String userId,
                     List<BIamUserRoleRelationEntity> entityList);

    void deleteByUserId(String userId);

    BIamUserRoleRelationEntity detail(String id);

    BIamUserRoleRelationEntity getByUk(String userId,
                                       String roleId);

    List<String> listUserIdByRoleIdSet(Set<String> roleIdSet);

    List<BIamUserRoleRelationEntity> listByUserId(String userId);

    List<BIamUserRoleRelationEntity> selectByRoleId(String roleId);

    List<BIamUserRoleRelationEntity> listByIdSet(Set<String> idSet);

    List<BIamUserRoleRelationEntity> listByRoleIdSet(Set<String> roleIdSet);

    List<BIamUserRoleRelationEntity> listByUserIdSet(Set<String> userIdSet);

    Map<String, Integer> countUserByRoleIdSet(Set<String> roleIdSet);

}
