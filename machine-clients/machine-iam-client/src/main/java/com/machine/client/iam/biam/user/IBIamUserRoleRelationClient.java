package com.machine.client.iam.biam.user;

import com.machine.client.iam.biam.user.dto.output.BIamUserRoleRelationListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/user_role_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamUserRoleRelationClient {

    @PostMapping("list_by_userId")
    List<BIamUserRoleRelationListOutputDto> listByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_idSet")
    List<BIamUserRoleRelationListOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_roleIdSet")
    List<BIamUserRoleRelationListOutputDto> listByRoleIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_userIdSet")
    List<BIamUserRoleRelationListOutputDto> listByUserIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("countUser_by_roleIdSet")
    Map<String, Integer> countUserByRoleIdSet(@RequestBody @Validated IdSetRequest request);
}



