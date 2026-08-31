package com.machine.app.admin.crm.customer.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.crm.customer.business.ICrmCustomerBusiness;
import com.machine.app.admin.crm.customer.controller.vo.response.CrmCustomerDetailResponseVo;
import com.machine.app.admin.crm.customer.controller.vo.response.CrmCustomerExpandListResponseVo;
import com.machine.app.admin.crm.customer.controller.vo.response.CrmCustomerListResponseVo;
import com.machine.app.admin.crm.customer.controller.vo.resquest.CrmCustomerCreateRequestVo;
import com.machine.app.admin.crm.customer.controller.vo.resquest.CrmCustomerQueryPageRequestVo;
import com.machine.app.admin.crm.customer.controller.vo.resquest.CrmCustomerUpdateRequestVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【CRM】客户")
@RestController
@RequestMapping("admin/crm/customer")
public class CrmCustomerController {

    @Autowired
    private ICrmCustomerBusiness crmCustomerBusiness;

    @Operation(summary = "创建客户")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:CUSTOMER:CREATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_CUSTOMER,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建客户")
    public IdResponse<String> create(@RequestBody @Validated CrmCustomerCreateRequestVo request) {
        log.info("创建客户，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(crmCustomerBusiness.create(request));
    }

    @Operation(summary = "删除客户")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:CUSTOMER:DELETE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_CUSTOMER,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除客户")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除客户，request={}", JSONUtil.toJsonStr(request));
        crmCustomerBusiness.delete(request);
    }

    @Operation(summary = "修改客户")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:CUSTOMER:UPDATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_CUSTOMER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改客户")
    public void update(@RequestBody @Validated CrmCustomerUpdateRequestVo request) {
        log.info("修改客户，request={}", JSONUtil.toJsonStr(request));
        crmCustomerBusiness.update(request);
    }

    @Operation(summary = "客户详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:CUSTOMER:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_CUSTOMER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询客户详情")
    public CrmCustomerDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return crmCustomerBusiness.detail(request);
    }

    @Operation(summary = "分页查询客户(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:CUSTOMER:PAGE_SIMPLE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_CUSTOMER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询客户(组件弹窗)")
    public PageResponse<CrmCustomerListResponseVo> pageSimple(@RequestBody @Validated CrmCustomerQueryPageRequestVo request) {
        return crmCustomerBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询客户(应用于客户管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:CUSTOMER:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_CUSTOMER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询客户(客户管理菜单)")
    public PageResponse<CrmCustomerExpandListResponseVo> pageExpand(@RequestBody @Validated CrmCustomerQueryPageRequestVo request) {
        return crmCustomerBusiness.pageExpand(request);
    }
}