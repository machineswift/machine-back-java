package com.machine.service.iam.user.dao;

import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleRelationEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IIamUserRoleRelationDao {

    String insert(String userId,
                  String roleId);

    void batchInsert(String userId,
                     List<IamUserRoleRelationEntity> entityList);

    void deleteByUserId(String userId);

    IamUserRoleRelationEntity detail(String id);

    IamUserRoleRelationEntity getByUk(String userId,
                                      String roleId);

    List<String> listUserIdByRoleIdSet(Set<String> roleIdSet);

    List<IamUserRoleRelationEntity> listByUserId(String userId);

    List<IamUserRoleRelationEntity> selectByRoleId(String roleId);

    List<IamUserRoleRelationEntity> listByIdSet(Set<String> idSet);

    List<IamUserRoleRelationEntity> listByRoleIdSet(Set<String> roleIdSet);

    List<IamUserRoleRelationEntity> listByUserIdSet(Set<String> userIdSet);

    Map<String, Integer> countUserByRoleIdSet(Set<String> roleIdSet);

}
