package com.machine.service.iam.user.dao;

import com.machine.sdk.common.envm.iam.UserDepartmentRelationTypeEnum;
import com.machine.service.iam.user.dao.mapper.entity.UserOrganizationRelationEntity;

import java.util.List;
import java.util.Set;

public interface IUserOrganizationRelationDao {

    int insert(String organizationId,
                String userId,
                UserDepartmentRelationTypeEnum type);


    int deleteLeaderByOrganizationId(String organizationId);


    UserOrganizationRelationEntity detail(String id);

    UserOrganizationRelationEntity selectLeaderByOrganizationId( String organizationId);

    List<UserOrganizationRelationEntity> mapLeaderByOrganizationIdSet(Set<String> organizationIdIdSet);

}
