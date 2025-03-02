package com.machine.service.iam.user.server;

import com.machine.client.iam.user.IIamUserPermissionClient;
import com.machine.client.iam.user.dto.input.DataPermission4ManageInputDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.service.iam.user.service.IUserPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/iam/user_permission")
public class IamUserPermissionServer implements IIamUserPermissionClient {

    @Autowired
    private IUserPermissionService userPermissionService;

    @Override
    @GetMapping("data_permission_4_superApp")
    public DataPermissionDto dataPermission4SuperApp() {
        return userPermissionService.dataPermission4SuperApp();
    }

    @Override
    @PostMapping("data_permission_4_manage")
    public DataPermissionDto dataPermission4SuperManage(@RequestBody @Validated DataPermission4ManageInputDto inputDto) {
        return userPermissionService.dataPermission4SuperManage(inputDto);
    }

}
