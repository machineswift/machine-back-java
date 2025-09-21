package com.machine.service.iam.user.dao;

import com.machine.service.iam.user.dao.mapper.entity.IamUserOrganizationRelationEntity;

import java.util.List;
import java.util.Set;

public interface IIamUserOrganizationRelationDao {

    void insertByUserId(String userId,
                        Set<String> organizationIdSet);

    void deleteByUserId(String userId,
                        Set<String> organizationIdSet);

    boolean isAssociationUserByOrganizationId(String organizationId);

    IamUserOrganizationRelationEntity detail(String id);

    List<String> listUserIdByOrganizationIdSet(Set<String> organizationIdSet);

    List<IamUserOrganizationRelationEntity> listByUserId(String userId);

    List<IamUserOrganizationRelationEntity> listByOrganizationIdSet(Set<String> organizationIdIdSet);

}
