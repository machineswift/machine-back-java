package com.machine.service.iam.role.dao;

import com.machine.service.iam.role.dao.mapper.entity.RolePermissionRelationEntity;

import java.util.List;

public interface IRolePermissionRelationDao {

    void insert(List<RolePermissionRelationEntity> entityList);

    int deleteByRoleId(String roleId);

    List<RolePermissionRelationEntity> selectByRoleId(String roleId);

    List<RolePermissionRelationEntity> selectByPermissionId(String permissionId);

}
