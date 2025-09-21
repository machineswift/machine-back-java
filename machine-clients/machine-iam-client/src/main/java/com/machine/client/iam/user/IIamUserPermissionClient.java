package com.machine.client.iam.user;

import com.machine.client.iam.user.dto.input.IamDataPermission4ManageInputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "xijie-iam-service/xijie-iam-service/server/iam/user_permission",
        configuration = OpenFeignDefaultConfig.class)
public interface IIamUserPermissionClient {

    @GetMapping("data_permission_4_superApp")
    DataPermissionDto dataPermission4SuperApp();

    @PostMapping("data_permission_4_manage")
    DataPermissionDto dataPermission4Manage(@RequestBody @Validated IamDataPermission4ManageInputDto inputDto);

}
