package com.machine.service.iam.user.dao;

import com.machine.sdk.common.envm.iam.IamUserRoleBusinessTypeEnum;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleBusinessRelationEntity;

import java.util.List;
import java.util.Set;

public interface IIamUserRoleBusinessRelationDao {

    String insert(String userRoleRelationId,
                  String businessId,
                  IamUserRoleBusinessTypeEnum businessType);

    void batchInsert(String userId,
                     List<IamUserRoleBusinessRelationEntity> entityList);

    int deleteByUk(String userRoleRelationId,
                   String businessId,
                   IamUserRoleBusinessTypeEnum businessType);

    void deleteByUserRoleRelationIdSet(String userId,
                                       Set<String> userRoleRelationIdSet);

    IamUserRoleBusinessRelationEntity getByUk(String userRoleRelationId,
                                              String businessId,
                                              IamUserRoleBusinessTypeEnum businessType);

    List<IamUserRoleBusinessRelationEntity> listByUserRoleRelationIdSet(Set<String> userRoleRelationIdSet);

    List<IamUserRoleBusinessRelationEntity> listByBusinessIdSet(IamUserRoleBusinessTypeEnum businessType,
                                                                Set<String> businessIdSet);


}
