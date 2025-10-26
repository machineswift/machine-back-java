package com.machine.app.manage.crm.customer.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.crm.customer.business.ICrmCustomerBusiness;
import com.machine.app.manage.crm.customer.controller.vo.response.CrmCustomerDetailResponseVo;
import com.machine.app.manage.crm.customer.controller.vo.response.CrmCustomerExpandListResponseVo;
import com.machine.app.manage.crm.customer.controller.vo.response.CrmCustomerListResponseVo;
import com.machine.app.manage.crm.customer.controller.vo.resquest.CrmCustomerCreateRequestVo;
import com.machine.app.manage.crm.customer.controller.vo.resquest.CrmCustomerQueryPageRequestVo;
import com.machine.app.manage.crm.customer.controller.vo.resquest.CrmCustomerUpdateRequestVo;
import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberDetailResponseVo;
import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberExpandListResponseVo;
import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberListResponseVo;
import com.machine.app.manage.crm.member.controller.vo.resquest.CrmMemberCreateRequestVo;
import com.machine.app.manage.crm.member.controller.vo.resquest.CrmMemberUpdateRequestVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import com.machine.sdk.common.model.response.PageResponse;
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
@RequestMapping("manage/crm/customer")
public class CrmCustomerController {

    @Autowired
    private ICrmCustomerBusiness crmCustomerBusiness;

    @Operation(summary = "创建客户")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:CUSTOMER:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated CrmCustomerCreateRequestVo request) {
        log.info("创建客户，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(crmCustomerBusiness.create(request));
    }

    @Operation(summary = "删除客户")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:CUSTOMER:DELETE')")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除客户，request={}", JSONUtil.toJsonStr(request));
        crmCustomerBusiness.delete(request);
    }

    @Operation(summary = "修改客户")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:CUSTOMER:UPDATE')")
    public void update(@RequestBody @Validated CrmCustomerUpdateRequestVo request) {
        log.info("修改客户，request={}", JSONUtil.toJsonStr(request));
        crmCustomerBusiness.update(request);
    }

    @Operation(summary = "客户详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:CUSTOMER:DETAIL')")
    public CrmCustomerDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return crmCustomerBusiness.detail(request);
    }

    @Operation(summary = "分页查询客户(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:CUSTOMER:PAGE_SIMPLE')")
    public PageResponse<CrmCustomerListResponseVo> pageSimple(@RequestBody @Validated CrmCustomerQueryPageRequestVo request) {
        return crmCustomerBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询客户(应用于客户管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:CUSTOMER:PAGE_EXPAND')")
    public PageResponse<CrmCustomerExpandListResponseVo> pageExpand(@RequestBody @Validated CrmCustomerQueryPageRequestVo request) {
        return crmCustomerBusiness.pageExpand(request);
    }
}