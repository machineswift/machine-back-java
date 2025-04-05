package com.machine.service.iam.user.dao;

import com.machine.service.iam.user.dao.mapper.entity.UserOrganizationRelationEntity;

import java.util.List;
import java.util.Set;

public interface IUserOrganizationRelationDao {

    int insert(String organizationId,
               String userId,
               UserDepartmentRelationTypeEnum type);

    void insertByUserId(String userId,
                        Set<String> organizationIdSet);

    void deleteByUserId(String userId,
                        Set<String> organizationIdSet);

    int deleteLeaderByOrganizationId(String organizationId);

    boolean isAssociationUserByOrganizationId(String organizationId);

    UserOrganizationRelationEntity detail(String id);

    UserOrganizationRelationEntity getLeaderByOrganizationId(String organizationId);

    List<UserOrganizationRelationEntity> listByUserId(String userId);

    List<UserOrganizationRelationEntity> listLeaderByOrganizationIdSet(Set<String> organizationIdIdSet);

    List<UserOrganizationRelationEntity> listByOrganizationIdSet(Set<String> organizationIdIdSet);

}
