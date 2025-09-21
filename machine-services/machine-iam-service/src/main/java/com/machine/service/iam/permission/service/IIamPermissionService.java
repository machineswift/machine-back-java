package com.machine.service.iam.permission.service;

import com.machine.client.iam.permission.dto.input.*;
import com.machine.client.iam.permission.dto.output.IamPermissionDetailOutputDto;
import com.machine.client.iam.permission.dto.output.IamPermissionListOutputDto;
import com.machine.client.iam.permission.dto.output.IamPermissionTreeOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IIamPermissionService {
    String create(IamPermissionCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(IamPermissionUpdateInputDto inputDto);

    int updateParent(IamPermissionUpdateParentInputDto inputDto);

    IamPermissionDetailOutputDto detail(IdRequest request);

    IamPermissionDetailOutputDto detailByCode(IdRequest request);

    List<IamPermissionListOutputDto> listByRoleId(IdRequest request);

    IamPermissionTreeOutputDto treeAll();

}
