package com.machine.service.iam.permission.dao;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.iam.permission.dao.mapper.entity.PermissionEntity;

import java.util.List;

public interface IPermissionDao {

    String insert(PermissionEntity entity);

    int delete(String id);

    int updateStatus(String id,
                     StatusEnum status);

    int updateParent(String id,
                     String parentId);

    int update(PermissionEntity entity);

    PermissionEntity getById(String id);

    PermissionEntity getByCode(String code);

    PermissionEntity getByParentIdAndName(String parentId,
                                          String name);

    List<PermissionEntity> listByRoleId(String roleId);

    List<PermissionEntity> selectByUserId(String userId);

    List<PermissionEntity> selectByRoleIds(List<String> roleIdList);

    List<PermissionEntity> listAll();

}
