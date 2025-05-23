package com.machine.client.iam.user;

import com.machine.client.iam.user.dto.output.IamUserRoleRelationListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/user_role_relation",
        configuration = OpenFeignDefaultConfig.class)
public interface IIamUserRoleRelationClient {

    @PostMapping("list_by_userId")
    List<IamUserRoleRelationListOutputDto> listByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_idSet")
    List<IamUserRoleRelationListOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_roleIdSet")
    List<IamUserRoleRelationListOutputDto> listByRoleIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_userIdSet")
    List<IamUserRoleRelationListOutputDto> listByUserIdSet(@RequestBody @Validated IdSetRequest request);
}
