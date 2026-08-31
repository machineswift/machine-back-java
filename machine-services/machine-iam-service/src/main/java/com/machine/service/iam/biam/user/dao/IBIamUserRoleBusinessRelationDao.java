package com.machine.service.iam.biam.user.dao;

import com.machine.sdk.base.envm.biam.role.BIamUserRoleBusinessTypeEnum;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleBusinessRelationEntity;

import java.util.List;
import java.util.Set;

public interface IBIamUserRoleBusinessRelationDao {

    String insert(String userRoleRelationId,
                  String businessId,
                  BIamUserRoleBusinessTypeEnum businessType);

    void batchInsert(String userId,
                     List<BIamUserRoleBusinessRelationEntity> entityList);

    int deleteByUk(String userRoleRelationId,
                   String businessId,
                   BIamUserRoleBusinessTypeEnum businessType);

    void deleteByUserRoleRelationIdSet(String userId,
                                       Set<String> userRoleRelationIdSet);

    BIamUserRoleBusinessRelationEntity getByUk(String userRoleRelationId,
                                               String businessId,
                                               BIamUserRoleBusinessTypeEnum businessType);

    List<BIamUserRoleBusinessRelationEntity> listByUserRoleRelationIdSet(Set<String> userRoleRelationIdSet);

    List<BIamUserRoleBusinessRelationEntity> listByBusinessIdSet(BIamUserRoleBusinessTypeEnum businessType,
                                                                 Set<String> businessIdSet);


}
