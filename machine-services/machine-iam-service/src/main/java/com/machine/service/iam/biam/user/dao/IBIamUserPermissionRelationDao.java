package com.machine.service.iam.biam.user.dao;

import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserPermissionRelationEntity;

import java.util.List;

public interface IBIamUserPermissionRelationDao {
    List<BIamUserPermissionRelationEntity> selectByPermissionId(String permissionId);
}
