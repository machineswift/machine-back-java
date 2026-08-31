package com.machine.client.iam.biam.user;

import com.machine.client.iam.biam.user.dto.input.BIamDataPermission4ManageInputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/user_permission",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamUserPermissionClient {

    @GetMapping("data_permission_4_superApp")
    BIamDataPermissionDto dataPermission4SuperApp();

    @PostMapping("data_permission_4_manage")
    BIamDataPermissionDto dataPermission4Manage(@RequestBody @Validated BIamDataPermission4ManageInputDto inputDto);

}



