package com.machine.service.iam.biam.user.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.user.IBIamUserRoleBusinessRelationClient;
import com.machine.client.iam.biam.userbk.dto.input.IamUserRoleInfoFranchiseeBindShopInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamUserRoleInfoFranchiseeUnBindShopInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserRoleBusinessRelationListOutputDto;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.iam.biam.user.service.IBIamUserRoleBusinessRelationService;
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
@RequestMapping("server/iam/biam/user_role_business_relation")
public class BIamUserRoleBusinessRelationServer implements IBIamUserRoleBusinessRelationClient {

    @Autowired
    private IBIamUserRoleBusinessRelationService userRoleBusinessRelationService;

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
    public List<BIamUserRoleBusinessRelationListOutputDto> listByShopIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleBusinessRelationService.listByShopIdSet(request);
    }

    @Override
    @PostMapping("list_by_userRoleRelationIdSet")
    public List<BIamUserRoleBusinessRelationListOutputDto> listByUserRoleRelationIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleBusinessRelationService.listByUserRoleRelationIdSet(request);
    }

}
