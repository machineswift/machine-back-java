package com.machine.service.iam.user.dao;

import com.machine.service.iam.user.dao.mapper.entity.UserPermissionRelationEntity;

import java.util.List;

public interface IUserPermissionRelationDao {
    List<UserPermissionRelationEntity> selectByPermissionId(String permissionId);
}
