package com.machine.app.iam.biam.organization.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.organization.business.IBIamOrganizationBusiness;
import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationCreateRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationQueryTreeRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationUpdateParentRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationUpdateRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.response.BIamOrganizationDetailResponseVo;
import com.machine.app.iam.biam.organization.controller.vo.response.BIamOrganizationExpandTreeResponseVo;
import com.machine.app.iam.biam.organization.controller.vo.response.BIamOrganizationWithShopTreeResponseVo;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import com.machine.starter.web.operateLog.annotation.WebOperationLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【BIAM】组织模块")
@RestController
@RequestMapping("iam/biam/organization")
public class BIamOrganizationController {

    @Autowired
    private IBIamOrganizationBusiness organizationBusiness;

    @Operation(summary = "创建组织")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ORGANIZATION:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ORGANIZATION,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建组织",
            moduleEntityId = "#request.name",
            content = "'创建组织：' + #request.name",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated BIamOrganizationCreateRequestVo request) {
        log.info("创建组织，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(organizationBusiness.create(request));
    }

    @Operation(summary = "删除组织")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ORGANIZATION:DELETE')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ORGANIZATION,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除组织",
            moduleEntityId = "#request.id",
            content = "'删除组织：' + #request.id")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除组织，request={}", JSONUtil.toJsonStr(request));
        organizationBusiness.delete(request);
    }

    @Operation(summary = "修改组织")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ORGANIZATION:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ORGANIZATION,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改组织",
            moduleEntityId = "#request.id",
            content = "'修改组织：' + #request.name")
    public void update(@RequestBody @Validated BIamOrganizationUpdateRequestVo request) {
        log.info("修改组织，request={}", JSONUtil.toJsonStr(request));
        organizationBusiness.update(request);
    }

    @Operation(summary = "修改父组织ID")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ORGANIZATION:UPDATE_PARENT')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ORGANIZATION,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改组织父节点",
            moduleEntityId = "#request.id",
            content = "'修改组织父节点：' + #request.id")
    public void updateParent(@RequestBody @Validated BIamOrganizationUpdateParentRequestVo request) {
        log.info("修改父组织，request={}", JSONUtil.toJsonStr(request));
        organizationBusiness.updateParent(request);
    }

    @Operation(summary = "组织详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ORGANIZATION:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ORGANIZATION,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询组织详情")
    public BIamOrganizationDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return organizationBusiness.detail(request);
    }

    @Operation(summary = "组织树(应用于组件弹窗)")
    @PostMapping("tree_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ORGANIZATION:TREE_SIMPLE')")
    public BIamOrganizationTreeSimpleOutputDto treeSimple(
            @RequestBody @Validated BIamOrganizationQueryTreeRequestVo request) {
        return organizationBusiness.treeSimple(request);
    }

    @Operation(summary = "组织树(应用于组织管理菜单)")
    @PostMapping("tree_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ORGANIZATION:TREE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ORGANIZATION,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询组织树(组织管理菜单)")
    public BIamOrganizationExpandTreeResponseVo treeExpand(
            @RequestBody @Validated BIamOrganizationQueryTreeRequestVo request) {
        return organizationBusiness.treeExpand(request);
    }

    @Operation(summary = "组织树(关联门店信息)")
    @PostMapping("tree_with_shop")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ORGANIZATION:TREE_WITH_SHOP')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ORGANIZATION,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询组织树(关联门店信息)")
    public BIamOrganizationWithShopTreeResponseVo treeWithShop(
            @RequestBody @Validated BIamOrganizationQueryTreeRequestVo request) {
        return organizationBusiness.treeExpandWithShop(request);
    }

}