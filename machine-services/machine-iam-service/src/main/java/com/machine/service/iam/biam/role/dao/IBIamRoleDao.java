package com.machine.service.iam.biam.role.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.role.dto.input.BIamRoleListSubInputDto;
import com.machine.client.iam.biam.role.dto.input.BIamRoleQueryPageInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRoleEntity;

import java.util.List;
import java.util.Set;

public interface IBIamRoleDao {

    String insert(BIamRoleEntity entity);

    int delete(String id);

    int updateStatus(String roleId,
                     StatusEnum status);

    int update(BIamRoleEntity entity);

    long countByIds(Set<String> idSet);

    BIamRoleEntity getById(String roleId);

    BIamRoleEntity getByName(String name);

    List<String> listSubId(BIamRoleListSubInputDto inputDto);

    List<String> listParentByTarget(String id);

    List<String> listAllCode();

    List<BIamRoleEntity> listSub(BIamRoleListSubInputDto inputDto);

    List<BIamRoleEntity> selectByUserId(String userId);

    List<BIamRoleEntity> selectByIdSet(Set<String> idSet);

    Page<BIamRoleEntity> selectPage(BIamRoleQueryPageInputDto inputDto);

}
