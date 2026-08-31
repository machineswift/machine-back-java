package com.machine.service.iam.biam.user.server;

import com.machine.client.iam.biam.user.IBIamUserPermissionClient;
import com.machine.client.iam.biam.user.dto.input.BIamDataPermission4ManageInputDto;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionDto;
import com.machine.service.iam.biam.user.service.IBIamUserPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/user_permission")
public class BIamUserPermissionServer implements IBIamUserPermissionClient {

    @Autowired
    private IBIamUserPermissionService userPermissionService;

    @Override
    @GetMapping("data_permission_4_superApp")
    public BIamDataPermissionDto dataPermission4SuperApp() {
        return userPermissionService.dataPermission4SuperApp();
    }

    @Override
    @PostMapping("data_permission_4_manage")
    public BIamDataPermissionDto dataPermission4Manage(@RequestBody @Validated BIamDataPermission4ManageInputDto inputDto) {
        return userPermissionService.dataPermission4Manage(inputDto);
    }

}
