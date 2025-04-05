package com.machine.service.iam.user.dao;

import com.machine.sdk.common.envm.iam.UserRoleBusinessTypeEnum;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleBusinessRelationEntity;

import java.util.List;
import java.util.Set;

public interface IUserRoleBusinessRelationDao {

    String insert(String userRoleRelationId,
                  String businessId,
                  UserRoleBusinessTypeEnum businessType);

    void batchInsert(String userId,
                     List<UserRoleBusinessRelationEntity> entityList);

    int deleteByUk(String userRoleRelationId,
                   String businessId,
                   UserRoleBusinessTypeEnum businessType);

    void deleteByUserRoleRelationIdSet(String userId,
                                       Set<String> userRoleRelationIdSet);

    UserRoleBusinessRelationEntity getByUk(String userRoleRelationId,
                                           String businessId,
                                           UserRoleBusinessTypeEnum businessType);

    List<UserRoleBusinessRelationEntity> listByUserRoleRelationIdSet( Set<String> userRoleRelationIdSet);

    List<UserRoleBusinessRelationEntity> listByBusinessIdSet(Set<String> businessIdSet,
                                                             UserRoleBusinessTypeEnum businessType);


}
