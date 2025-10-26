package com.machine.client.iam.user;

import com.machine.client.iam.userbk.dto.input.IamUserRoleInfoFranchiseeBindShopInputDto;
import com.machine.client.iam.userbk.dto.input.IamUserRoleInfoFranchiseeUnBindShopInputDto;
import com.machine.client.iam.user.dto.output.IamUserRoleBusinessRelationListOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-iam-service", path = "machine-iam-service/server/iam/user_role_business_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IIamUserRoleBusinessRelationClient {

    @PostMapping("bind_franchisee_shop")
    boolean bindFranchiseeShop(@RequestBody @Validated IamUserRoleInfoFranchiseeBindShopInputDto inputDto);

    @PostMapping("unbind_franchisee_shop")
    boolean unbindFranchiseeShop(@RequestBody @Validated IamUserRoleInfoFranchiseeUnBindShopInputDto inputDto);

    @PostMapping("list_by_shopIdSet")
    List<IamUserRoleBusinessRelationListOutputDto> listByShopIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_userRoleRelationIdSet")
    List<IamUserRoleBusinessRelationListOutputDto> listByUserRoleRelationIdSet(@RequestBody @Validated IdSetRequest request);
}



