package com.machine.client.iam.role;

import com.machine.client.iam.role.dto.output.IamRolePermissionListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/role_permission", configuration = OpenFeignDefaultConfig.class)
public interface IIamRolePermissionClient {

    @PostMapping("listByRoleId")
    List<IamRolePermissionListOutputDto> listByRoleId(@RequestBody @Validated IdRequest request);

}
