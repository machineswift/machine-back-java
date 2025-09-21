package com.machine.service.iam.user.service;


import com.machine.client.iam.user.dto.input.IamDataPermission4ManageInputDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;

public interface IIamUserPermissionService {

    DataPermissionDto dataPermission4SuperApp();

    DataPermissionDto dataPermission4Manage(IamDataPermission4ManageInputDto inputDto);

}
