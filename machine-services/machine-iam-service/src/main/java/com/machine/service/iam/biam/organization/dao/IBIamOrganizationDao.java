package com.machine.service.iam.biam.organization.dao;

import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.service.iam.biam.organization.dao.mapper.entity.BIamOrganizationEntity;

import java.util.List;

public interface IBIamOrganizationDao {

    String insert(BIamOrganizationEntity entity);

    int delete(String id);

    int update(BIamOrganizationEntity entity);

    int updateParentId(String id,
                       String parentId);

    BIamOrganizationEntity getById(String id);

    BIamOrganizationEntity getByParentIdAndName(String parentId,
                                                String name);

    List<BIamOrganizationEntity> listAllByType(BIamOrganizationTypeEnum organizationType);
}
