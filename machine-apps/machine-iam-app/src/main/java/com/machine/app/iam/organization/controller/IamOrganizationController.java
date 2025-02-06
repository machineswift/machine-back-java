package com.machine.app.iam.organization.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.organization.business.IIamOrganizationBusiness;
import com.machine.app.iam.organization.controller.vo.request.*;
import com.machine.app.iam.organization.controller.vo.response.*;
import com.machine.client.iam.organization.dto.input.IamOrganizationDetailByNameInputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【IAM】组织模块")
@RestController
@RequestMapping("iam/organization")
public class IamOrganizationController {

    @Autowired
    private IIamOrganizationBusiness organizationBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    public IdResponse<String> create(@RequestBody @Validated IamOrganizationCreateRequestVo request) {
        log.info("创建组织，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(organizationBusiness.create(request));
    }

    @Operation(summary = "删除")
    @PostMapping("delete")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除组织，request={}", JSONUtil.toJsonStr(request));
        organizationBusiness.delete(request);
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    public void update(@RequestBody @Validated IamOrganizationUpdateRequestVo request) {
        log.info("修改组织，request={}", JSONUtil.toJsonStr(request));
        organizationBusiness.update(request);
    }

    @Operation(summary = "修改父ID")
    @PostMapping("update_parent")
    public void updateParent(@RequestBody @Validated IamOrganizationUpdateParentRequestVo request) {
        log.info("修改父组织，request={}", JSONUtil.toJsonStr(request));
        organizationBusiness.updateParent(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    public IamOrganizationDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return organizationBusiness.detail(request);
    }

    @Hidden
    @PostMapping("detail_by_name")
    public IamOrganizationDetailResponseVo detailByName(@RequestBody @Validated IamOrganizationDetailByNameInputDto inputDto) {
        return organizationBusiness.detailByName(inputDto);
    }

    @Operation(summary = "树(应用于组件弹窗)")
    @PostMapping("tree_all_simple")
    public IamOrganizationTreeSimpleOutputDto treeAllSimple(@RequestBody @Validated IamOrganizationQueryTreeRequestVo request) {
        return organizationBusiness.treeAllSimple(request);
    }

    @Operation(summary = "树(扩充，应用于组织管理菜单)")
    @PostMapping("tree_all_expand")
    public IamOrganizationExpandTreeResponseVo treeAllExpand(@RequestBody @Validated IamOrganizationQueryTreeRequestVo request) {
        return organizationBusiness.treeAllExpand(request);
    }

    @Operation(summary = "树(扩充，带上门店信息)")
    @PostMapping("tree_all_expand_with_shop")
    public IamOrganizationExpandWithShopTreeResponseVo treeAllExpandWithShop(@RequestBody @Validated IamOrganizationQueryTreeRequestVo request) {
        return organizationBusiness.treeAllExpandWithShop(request);
    }

}