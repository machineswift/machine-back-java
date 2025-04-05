package com.machine.service.iam.user.dao;

import com.machine.service.iam.user.dao.mapper.entity.UserRoleRelationEntity;

import java.util.List;
import java.util.Set;

public interface IUserRoleRelationDao {

    String insert(String userId,
                  String roleId);

    void insertByUserId(String userId,
                        Set<String> roleIdSet);

    void deleteByUserId(String userId,
                        Set<String> roleIdSet);

    UserRoleRelationEntity detail(String id);

    UserRoleRelationEntity getByUk(String userId,
                                   String roleId);

    List<UserRoleRelationEntity> listByUserId(String userId);

    List<UserRoleRelationEntity> selectByRoleId(String roleId);

    List<UserRoleRelationEntity> listByIdSet(Set<String> idSet);

    List<UserRoleRelationEntity> listByRoleIdSet(Set<String> roleIdSet);

    List<UserRoleRelationEntity> listByUserIdSet(Set<String> userIdSet);

}
