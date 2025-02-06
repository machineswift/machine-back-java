package com.machine.service.iam.user.dao;

import com.machine.client.iam.organization.dto.input.IamUserRoleTargetQueryListInputDto;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleTargetRelationEntity;

import java.util.List;
import java.util.Set;

public interface IUserRoleTargetRelationDao {

    void insert(String userId,
                UserRoleTargetRelationEntity entity);

    void batchInsert(String userId,
                     List<UserRoleTargetRelationEntity> entityList);

    int updateTargetNull(String userId,
                         String id);

    int delete(UserRoleTargetRelationEntity entity);

    void deleteByUserId(String userId);

    void deleteByIdSet(String userId,
                       Set<String> deleteIdSet);

    void deleteByRoleIdSet(String userId,
                       Set<String> roleIdSet);

    int update(UserRoleTargetRelationEntity entity);

    Boolean isAssociationRoleByOrganizationIdSet(Set<String> organizationIdSet);

    List<String> listUserIdByOrganizationIdSet(Set<String> organizationIdSet);

    List<UserRoleTargetRelationEntity> listByUserId(String userId);

    List<UserRoleTargetRelationEntity> listByUserIdSet(Set<String> userIdSet);

    List<UserRoleTargetRelationEntity> selectByRoleId(String roleId);

    List<UserRoleTargetRelationEntity> listByUserAndRoleIdId(String userId,
                                                             String roleId);

    List<UserRoleTargetRelationEntity> listOrganizationShopByRoleIdSet(Set<String> idSet);

    List<UserRoleTargetRelationEntity> listByCondition(IamUserRoleTargetQueryListInputDto inputDto);

}
