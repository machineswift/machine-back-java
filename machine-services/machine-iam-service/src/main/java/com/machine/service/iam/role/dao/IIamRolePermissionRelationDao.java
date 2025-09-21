package com.machine.service.iam.role.dao;

import com.machine.service.iam.role.dao.mapper.entity.IamRolePermissionRelationEntity;

import java.util.Collection;
import java.util.List;

public interface IIamRolePermissionRelationDao {

    void insert(List<IamRolePermissionRelationEntity> entityList);

    int deleteByRoleId(String roleId);

    List<IamRolePermissionRelationEntity> selectByRoleId(String roleId);

    List<IamRolePermissionRelationEntity> selectByRoleIds(Collection<String> roleIds);

    List<IamRolePermissionRelationEntity> selectByPermissionId(String permissionId);

    List<IamRolePermissionRelationEntity> selectByPermissionCode(String permissionCode);

}
