package com.machine.service.iam.biam.user.dao;

import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserOrganizationRelationEntity;

import java.util.List;
import java.util.Set;

public interface IBIamUserOrganizationRelationDao {

    void insertByUserId(String userId,
                        Set<String> organizationIdSet);

    void deleteByUserId(String userId,
                        Set<String> organizationIdSet);

    boolean isAssociationUserByOrganizationId(String organizationId);

    BIamUserOrganizationRelationEntity detail(String id);

    List<String> listUserIdByOrganizationIdSet(Set<String> organizationIdSet);

    List<BIamUserOrganizationRelationEntity> listByUserId(String userId);

    List<BIamUserOrganizationRelationEntity> listByOrganizationIdSet(Set<String> organizationIdIdSet);

}
