package com.machine.service.iam.organization.dao;

import com.machine.service.iam.organization.dao.mapper.entity.OrganizationUserRelationEntity;

import java.util.List;
import java.util.Set;

public interface IOrganizationUserRelationDao {

    void insert(String organizationId,
                String userId);

    int deleteByOrganizationId(String organizationId);

    OrganizationUserRelationEntity selectByOrganizationId(String organizationId);

    List<OrganizationUserRelationEntity> selectByOrganizationIdSet(Set<String> organizationIdSet);
}
