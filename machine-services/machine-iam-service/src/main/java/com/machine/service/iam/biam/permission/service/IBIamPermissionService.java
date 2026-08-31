package com.machine.service.iam.biam.permission.service;

import com.machine.client.iam.biam.permission.dto.input.BIamPermissionCreateInputDto;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionUpdateInputDto;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionUpdateParentInputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionDetailOutputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionListOutputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.Tuples;

import java.util.List;

public interface IBIamPermissionService {
    String create(BIamPermissionCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(BIamPermissionUpdateInputDto inputDto);

    int updateParent(BIamPermissionUpdateParentInputDto inputDto);

    BIamPermissionDetailOutputDto detail(IdRequest request);

    BIamPermissionDetailOutputDto detailByCode(IdRequest request);

    List<BIamPermissionListOutputDto> listByRoleId(IdRequest request);

    List<BIamPermissionListOutputDto> listByRoleIdSet(IdSetRequest request);

    Tuples.Tuple2<String, BIamPermissionTreeOutputDto> treeAll();

}
