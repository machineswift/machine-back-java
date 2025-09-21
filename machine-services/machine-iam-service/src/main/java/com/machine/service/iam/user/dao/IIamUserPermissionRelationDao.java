package com.machine.service.iam.user.dao;

import com.machine.service.iam.user.dao.mapper.entity.IamUserPermissionRelationEntity;

import java.util.List;

public interface IIamUserPermissionRelationDao {
    List<IamUserPermissionRelationEntity> selectByPermissionId(String permissionId);
}
