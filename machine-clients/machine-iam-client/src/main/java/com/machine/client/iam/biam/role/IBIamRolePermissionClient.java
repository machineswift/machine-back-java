package com.machine.client.iam.biam.role;

import com.machine.client.iam.biam.role.dto.output.BIamRolePermissionListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/role_permission",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamRolePermissionClient {

    @PostMapping("listByRoleId")
    List<BIamRolePermissionListOutputDto> listByRoleId(@RequestBody @Validated IdRequest request);

}



