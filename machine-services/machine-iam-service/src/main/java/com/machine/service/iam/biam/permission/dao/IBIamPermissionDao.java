package com.machine.service.iam.biam.permission.dao;

import com.machine.service.iam.biam.permission.dao.mapper.entity.BIamPermissionEntity;

import java.util.Collection;
import java.util.List;

public interface IBIamPermissionDao {

    String insert(BIamPermissionEntity entity);

    int delete(String id);

    int updateParent(String id,
            String parentId);

    int update(BIamPermissionEntity entity);

    BIamPermissionEntity getById(String id);

    BIamPermissionEntity getByCode(String code);

    BIamPermissionEntity getByParentIdAndName(String parentId,
                                              String name);

    List<String> selectIdByRoleIds(List<String> roleIdList);

    List<BIamPermissionEntity> listByRoleId(String roleId);

    List<BIamPermissionEntity> listByRoleIdSet(Collection<String> roleIdSet);

    List<BIamPermissionEntity> selectByUserId(String userId);

    List<BIamPermissionEntity> selectByRoleIds(List<String> roleIdList);

    List<BIamPermissionEntity> listAll();

}
