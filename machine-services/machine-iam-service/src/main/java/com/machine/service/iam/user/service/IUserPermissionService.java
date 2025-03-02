package com.machine.service.iam.user.service;


import com.machine.client.iam.user.dto.input.DataPermission4ManageInputDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;

public interface IUserPermissionService {

    DataPermissionDto dataPermission4SuperApp();

    DataPermissionDto dataPermission4SuperManage(DataPermission4ManageInputDto inputDto);

}
