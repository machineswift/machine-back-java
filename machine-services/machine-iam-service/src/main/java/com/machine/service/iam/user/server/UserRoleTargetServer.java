package com.machine.service.iam.user.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.organization.dto.input.IamUserRoleTargetQueryListInputDto;
import com.machine.client.iam.organization.dto.output.IamUserRoleTargetListOutputDto;
import com.machine.client.iam.user.IIamUserRoleTargetClient;
import com.machine.client.iam.user.dto.input.DataPermission4ManageInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoBindFranchiseeShopInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoUnbindFranchiseeShopInputDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.service.IUserRoleTargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/user_role_target")
public class UserRoleTargetServer implements IIamUserRoleTargetClient {

    @Autowired
    private IUserRoleTargetService userRoleTargetService;

    @Override
    @PostMapping("bind_franchisee_shop")
    public boolean bindFranchiseeShop(IamUserRoleInfoBindFranchiseeShopInputDto inputDto) {
        log.info("绑定加盟商门店，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userRoleTargetService.bindFranchiseeShop(inputDto);
    }

    @Override
    @PostMapping("unbind_franchisee_shop")
    public boolean unbindFranchiseeShop(IamUserRoleInfoUnbindFranchiseeShopInputDto inputDto) {
        log.info("解绑加盟商门店，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userRoleTargetService.unbindFranchiseeShop(inputDto);
    }

    @Override
    @GetMapping("data_permission_4_superApp")
    public DataPermissionDto dataPermission4SuperApp() {
        return userRoleTargetService.dataPermission4SuperApp();
    }

    @Override
    @PostMapping("data_permission_4_manage")
    public DataPermissionDto dataPermission4SuperManage(@RequestBody @Validated DataPermission4ManageInputDto inputDto) {
        return userRoleTargetService.dataPermission4SuperManage(inputDto);
    }

    @Override
    @PostMapping("list_userId_by_organizationIdSet")
    public List<String> listUserIdByOrganizationIdSet(IdSetRequest request) {
        return userRoleTargetService.listUserIdByOrganizationIdSet(request);
    }

    @Override
    @PostMapping("list_by_userId")
    public List<IamUserRoleTargetListOutputDto> listByUserId(@RequestBody @Validated IdRequest request) {
        return userRoleTargetService.listByUserId(request);
    }

    @Override
    @PostMapping("list_by_userIdSet")
    public List<IamUserRoleTargetListOutputDto> listByUserIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleTargetService.listByUserIdSet(request);
    }

    @Override
    @PostMapping("list_by_roleIdSet")
    public List<IamUserRoleTargetListOutputDto> listByRoleIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleTargetService.listOrganizationShopByRoleIdSet(request);
    }

    @Override
    @PostMapping("list_by_condition")
    public List<IamUserRoleTargetListOutputDto> listByCondition(@RequestBody @Validated IamUserRoleTargetQueryListInputDto inputDto) {
        return userRoleTargetService.listByCondition(inputDto);
    }
}
