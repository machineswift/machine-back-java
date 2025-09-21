package com.machine.service.iam.role.service;

import com.machine.client.iam.role.dto.output.IamRolePermissionListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IIamRolePermissionService {


    List<IamRolePermissionListOutputDto> listByRoleId(IdRequest request);

}
