package com.machine.service.iam.user.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.user.IIamUserRoleBusinessRelationClient;
import com.machine.client.iam.userbk.dto.input.IamUserRoleInfoFranchiseeBindShopInputDto;
import com.machine.client.iam.userbk.dto.input.IamUserRoleInfoFranchiseeUnBindShopInputDto;
import com.machine.client.iam.user.dto.output.IamUserRoleBusinessRelationListOutputDto;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.service.IIamUserRoleBusinessRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/user_role_business_relation")
public class IamUserRoleBusinessRelationServer implements IIamUserRoleBusinessRelationClient {

    @Autowired
    private IIamUserRoleBusinessRelationService userRoleBusinessRelationService;

    @Override
    @PostMapping("bind_franchisee_shop")
    public boolean bindFranchiseeShop(@RequestBody @Validated IamUserRoleInfoFranchiseeBindShopInputDto inputDto) {
        log.info("加盟商绑定门店，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userRoleBusinessRelationService.bindFranchiseeShop(inputDto);
    }

    @Override
    @PostMapping("unbind_franchisee_shop")
    public boolean unbindFranchiseeShop(@RequestBody @Validated IamUserRoleInfoFranchiseeUnBindShopInputDto inputDto) {
        log.info("加盟商解绑门店，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userRoleBusinessRelationService.unbindFranchiseeShop(inputDto);
    }

    @Override
    @PostMapping("list_by_shopIdSet")
    public List<IamUserRoleBusinessRelationListOutputDto> listByShopIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleBusinessRelationService.listByShopIdSet(request);
    }

    @Override
    @PostMapping("list_by_userRoleRelationIdSet")
    public List<IamUserRoleBusinessRelationListOutputDto> listByUserRoleRelationIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleBusinessRelationService.listByUserRoleRelationIdSet(request);
    }

}
