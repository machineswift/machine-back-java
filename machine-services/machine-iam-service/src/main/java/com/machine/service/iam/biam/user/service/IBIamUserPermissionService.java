package com.machine.service.iam.biam.user.service;


import com.machine.client.iam.biam.user.dto.input.BIamDataPermission4ManageInputDto;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionDto;

public interface IBIamUserPermissionService {

    BIamDataPermissionDto dataPermission4SuperApp();

    BIamDataPermissionDto dataPermission4Manage(BIamDataPermission4ManageInputDto inputDto);

}
