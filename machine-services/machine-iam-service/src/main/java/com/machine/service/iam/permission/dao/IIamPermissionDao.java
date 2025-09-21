package com.machine.service.iam.permission.dao;

import com.machine.service.iam.permission.dao.mapper.entity.IamPermissionEntity;

import java.util.List;

public interface IIamPermissionDao {

    String insert(IamPermissionEntity entity);

    int delete(String id);

    int updateParent(String id,
                     String parentId);

    int update(IamPermissionEntity entity);

    IamPermissionEntity getById(String id);

    IamPermissionEntity getByCode(String code);

    IamPermissionEntity getByParentIdAndName(String parentId,
                                             String name);

    List<String> selectIdByRoleIds(List<String> roleIdList);

    List<IamPermissionEntity> listByRoleId(String roleId);

    List<IamPermissionEntity> selectByUserId(String userId);

    List<IamPermissionEntity> selectByRoleIds(List<String> roleIdList);

    List<IamPermissionEntity> listAll();

}
