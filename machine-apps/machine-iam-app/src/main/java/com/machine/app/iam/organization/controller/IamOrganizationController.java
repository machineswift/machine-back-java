package com.machine.app.iam.organization.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.organization.business.IIamOrganizationBusiness;
import com.machine.app.iam.organization.controller.vo.request.*;
import com.machine.app.iam.organization.controller.vo.response.*;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【IAM】组织模块")
@RestController
@RequestMapping("iam/organization")
public class IamOrganizationController {

    @Autowired
    private IIamOrganizationBusiness organizationBusiness;

    @Operation(summary = "创建组织")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ORGANIZATION:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated IamOrganizationCreateRequestVo request) {
        log.info("创建组织，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(organizationBusiness.create(request));
    }

    @Operation(summary = "删除组织")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ORGANIZATION:DELETE')")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除组织，request={}", JSONUtil.toJsonStr(request));
        organizationBusiness.delete(request);
    }

    @Operation(summary = "修改组织")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ORGANIZATION:UPDATE')")
    public void update(@RequestBody @Validated IamOrganizationUpdateRequestVo request) {
        log.info("修改组织，request={}", JSONUtil.toJsonStr(request));
        organizationBusiness.update(request);
    }

    @Operation(summary = "修改父组织ID")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ORGANIZATION:UPDATE_PARENT')")
    public void updateParent(@RequestBody @Validated IamOrganizationUpdateParentRequestVo request) {
        log.info("修改父组织，request={}", JSONUtil.toJsonStr(request));
        organizationBusiness.updateParent(request);
    }

    @Operation(summary = "组织详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ORGANIZATION:DETAIL')")
    public IamOrganizationDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return organizationBusiness.detail(request);
    }

    @Operation(summary = "组织树(应用于组件弹窗)")
    @PostMapping("tree_simple")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ORGANIZATION:TREE_SIMPLE')")
    public IamOrganizationTreeSimpleOutputDto treeSimple(@RequestBody @Validated IamOrganizationQueryTreeRequestVo request) {
        return organizationBusiness.treeSimple(request);
    }

    @Operation(summary = "组织树(应用于组织管理菜单)")
    @PostMapping("tree_expand")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ORGANIZATION:TREE_EXPAND')")
    public IamOrganizationExpandTreeResponseVo treeExpand(@RequestBody @Validated IamOrganizationQueryTreeRequestVo request) {
        return organizationBusiness.treeExpand(request);
    }

    @Operation(summary = "组织树(关联门店信息)")
    @PostMapping("tree_with_shop")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ORGANIZATION:TREE_WITH_SHOP')")
    public IamOrganizationWithShopTreeResponseVo treeWithShop(@RequestBody @Validated IamOrganizationQueryTreeRequestVo request) {
        return organizationBusiness.treeExpandWithShop(request);
    }

}