package com.machine.service.iam.organization.dao;

import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.service.iam.organization.dao.mapper.entity.IamOrganizationEntity;

import java.util.List;

public interface IIamOrganizationDao {

    String insert(IamOrganizationEntity entity);

    int delete(String id);

    int update(IamOrganizationEntity entity);

    int updateParentId(String id,
                       String parentId);

    IamOrganizationEntity getById(String id);

    IamOrganizationEntity getByParentIdAndName(String parentId,
                                               String name);

    List<IamOrganizationEntity> listAllByType(IamOrganizationTypeEnum organizationType);
}
