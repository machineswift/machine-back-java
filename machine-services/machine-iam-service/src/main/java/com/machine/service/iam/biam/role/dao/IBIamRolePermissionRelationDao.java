package com.machine.service.iam.biam.role.dao;

import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRolePermissionRelationEntity;

import java.util.Collection;
import java.util.List;

public interface IBIamRolePermissionRelationDao {

    void insert(List<BIamRolePermissionRelationEntity> entityList);

    int deleteByRoleId(String roleId);

    List<BIamRolePermissionRelationEntity> selectByRoleId(String roleId);

    List<BIamRolePermissionRelationEntity> selectByRoleIds(Collection<String> roleIds);

    List<BIamRolePermissionRelationEntity> selectByPermissionId(String permissionId);

    List<BIamRolePermissionRelationEntity> selectByPermissionCode(String permissionCode);

}
