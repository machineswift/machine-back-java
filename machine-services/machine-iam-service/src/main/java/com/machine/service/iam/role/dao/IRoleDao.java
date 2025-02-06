package com.machine.service.iam.role.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.role.dto.input.IamRoleListSubInputDto;
import com.machine.client.iam.role.dto.input.IamRoleQueryPageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import com.machine.service.iam.role.dao.mapper.entity.RoleEntity;

import java.util.List;
import java.util.Set;

public interface IRoleDao {

    String insert(RoleEntity entity);

    int delete(String id);

    int updateStatus(String roleId,
                     StatusEnum status);

    int update(RoleEntity entity);

    RoleEntity getById(String roleId);

    RoleEntity getByCode(String code);

    RoleEntity getByName(String name);

    List<String> listSubId(IamRoleListSubInputDto inputDto);

    List<String> listIdByType(RoleTypeEnum type);

    List<String> listParentByTarget(String id);

    List<String> listAllCode();

    List<RoleEntity> listSub(IamRoleListSubInputDto inputDto);

    List<RoleEntity> selectByUserId(String userId);

    List<RoleEntity> selectByIdSet(Set<String> idSet);

    Page<RoleEntity> selectPage(IamRoleQueryPageInputDto inputDto);

}
