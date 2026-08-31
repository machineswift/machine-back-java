package com.machine.service.iam.biam.role.service;

import com.machine.client.iam.biam.role.dto.output.BIamRolePermissionListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

public interface IBIamRolePermissionService {


    List<BIamRolePermissionListOutputDto> listByRoleId(IdRequest request);

}
