package com.machine.client.iam.user;

import com.machine.client.iam.organization.dto.input.IamUserRoleTargetQueryListInputDto;
import com.machine.client.iam.organization.dto.output.IamUserRoleTargetListOutputDto;
import com.machine.client.iam.user.dto.input.DataPermission4ManageInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoBindFranchiseeShopInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoUnbindFranchiseeShopInputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/user_role_target", configuration = OpenFeignDefaultConfig.class)
public interface IIamUserRoleTargetClient {

    @PostMapping("bind_franchisee_shop")
    boolean bindFranchiseeShop(@RequestBody @Validated IamUserRoleInfoBindFranchiseeShopInputDto inputDto);

    @PostMapping("unbind_franchisee_shop")
    boolean unbindFranchiseeShop(@RequestBody @Validated IamUserRoleInfoUnbindFranchiseeShopInputDto inputDto);

    @GetMapping("data_permission_4_superApp")
    DataPermissionDto dataPermission4SuperApp();

    @PostMapping("data_permission_4_manage")
    DataPermissionDto dataPermission4SuperManage(@RequestBody @Validated DataPermission4ManageInputDto inputDto);

    @PostMapping("list_userId_by_organizationIdSet")
    List<String> listUserIdByOrganizationIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_userId")
    List<IamUserRoleTargetListOutputDto> listByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_userIdSet")
    List<IamUserRoleTargetListOutputDto> listByUserIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_roleIdSet")
    List<IamUserRoleTargetListOutputDto> listByRoleIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_condition")
    List<IamUserRoleTargetListOutputDto> listByCondition(@RequestBody @Validated IamUserRoleTargetQueryListInputDto inputDto);

}
