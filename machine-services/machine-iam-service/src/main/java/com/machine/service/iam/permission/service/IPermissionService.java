package com.machine.service.iam.permission.service;

import com.machine.client.iam.permission.dto.input.*;
import com.machine.client.iam.permission.dto.output.PermissionDetailOutputDto;
import com.machine.client.iam.permission.dto.output.PermissionListOutputDto;
import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IPermissionService {
    String create(IamPermissionCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(IamPermissionUpdateInputDto inputDto);

    int updateStatus(IamPermissionUpdateStatusInputDto inputDto);

    int updateParent(IamPermissionUpdateParentInputDto inputDto);

    PermissionDetailOutputDto detail(IdRequest request);

    List<PermissionListOutputDto> listByRoleId(IdRequest request);

    PermissionTreeOutputDto treeAll();

}
