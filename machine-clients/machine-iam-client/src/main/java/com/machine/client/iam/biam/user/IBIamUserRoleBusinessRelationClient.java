package com.machine.client.iam.biam.user;

import com.machine.client.iam.biam.userbk.dto.input.IamUserRoleInfoFranchiseeBindShopInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamUserRoleInfoFranchiseeUnBindShopInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserRoleBusinessRelationListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/user_role_business_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamUserRoleBusinessRelationClient {

    @PostMapping("bind_franchisee_shop")
    boolean bindFranchiseeShop(@RequestBody @Validated IamUserRoleInfoFranchiseeBindShopInputDto inputDto);

    @PostMapping("unbind_franchisee_shop")
    boolean unbindFranchiseeShop(@RequestBody @Validated IamUserRoleInfoFranchiseeUnBindShopInputDto inputDto);

    @PostMapping("list_by_shopIdSet")
    List<BIamUserRoleBusinessRelationListOutputDto> listByShopIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_userRoleRelationIdSet")
    List<BIamUserRoleBusinessRelationListOutputDto> listByUserRoleRelationIdSet(@RequestBody @Validated IdSetRequest request);
}



