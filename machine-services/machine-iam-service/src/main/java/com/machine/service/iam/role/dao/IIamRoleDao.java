package com.machine.service.iam.role.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.role.dto.input.IamRoleListSubInputDto;
import com.machine.client.iam.role.dto.input.IamRoleQueryPageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.iam.role.dao.mapper.entity.IamRoleEntity;

import java.util.List;
import java.util.Set;

public interface IIamRoleDao {

    String insert(IamRoleEntity entity);

    int delete(String id);

    int updateStatus(String roleId,
                     StatusEnum status);

    int update(IamRoleEntity entity);

    IamRoleEntity getById(String roleId);

    IamRoleEntity getByName(String name);

    List<String> listSubId(IamRoleListSubInputDto inputDto);

    List<String> listParentByTarget(String id);

    List<String> listAllCode();

    List<IamRoleEntity> listSub(IamRoleListSubInputDto inputDto);

    List<IamRoleEntity> selectByUserId(String userId);

    List<IamRoleEntity> selectByIdSet(Set<String> idSet);

    Page<IamRoleEntity> selectPage(IamRoleQueryPageInputDto inputDto);

}
