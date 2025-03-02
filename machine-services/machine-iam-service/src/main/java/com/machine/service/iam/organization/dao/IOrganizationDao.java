package com.machine.service.iam.organization.dao;

import com.machine.sdk.common.envm.iam.organization.OrganizationTypeEnum;
import com.machine.service.iam.organization.dao.mapper.entity.OrganizationEntity;

import java.util.List;
import java.util.Set;

public interface IOrganizationDao {

    String insert(OrganizationEntity entity);

    int delete(String id);

    int update(OrganizationEntity entity);

    OrganizationEntity getById(String id);

    OrganizationEntity getByParentIdAndName(String parentId,
                                            String name);

    List<OrganizationEntity> selectByIdSet(Set<String> idSet);

    List<OrganizationEntity> listAllByType(OrganizationTypeEnum organizationType);
}
