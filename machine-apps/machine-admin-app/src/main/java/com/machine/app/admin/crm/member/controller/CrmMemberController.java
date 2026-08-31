package com.machine.app.admin.crm.member.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.crm.member.business.ICrmMemberBusiness;
import com.machine.app.admin.crm.member.controller.vo.response.CrmMemberDetailResponseVo;
import com.machine.app.admin.crm.member.controller.vo.response.CrmMemberExpandListResponseVo;
import com.machine.app.admin.crm.member.controller.vo.response.CrmMemberListResponseVo;
import com.machine.app.admin.crm.member.controller.vo.resquest.CrmMemberCreateRequestVo;
import com.machine.app.admin.crm.member.controller.vo.resquest.CrmMemberQueryPageRequestVo;
import com.machine.app.admin.crm.member.controller.vo.resquest.CrmMemberUpdateRequestVo;
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
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【CRM】会员")
@RestController
@RequestMapping("admin/crm/member")
public class CrmMemberController {

    @Autowired
    private ICrmMemberBusiness crmMemberBusiness;
    
    @Operation(summary = "创建会员")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:MEMBER:CREATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_MEMBER,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建会员")
    public IdResponse<String> create(@RequestBody @Validated CrmMemberCreateRequestVo request) {
        log.info("创建会员，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(crmMemberBusiness.create(request));
    }

    @Operation(summary = "删除会员")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:MEMBER:DELETE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_MEMBER,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除会员")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除会员，request={}", JSONUtil.toJsonStr(request));
        crmMemberBusiness.delete(request);
    }

    @Operation(summary = "修改会员")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:MEMBER:UPDATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_MEMBER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改会员")
    public void update(@RequestBody @Validated CrmMemberUpdateRequestVo request) {
        log.info("修改会员，request={}", JSONUtil.toJsonStr(request));
        crmMemberBusiness.update(request);
    }

    @Operation(summary = "会员详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:MEMBER:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_MEMBER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询会员详情")
    public CrmMemberDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return crmMemberBusiness.detail(request);
    }

    @Operation(summary = "分页查询会员(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:MEMBER:PAGE_SIMPLE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_MEMBER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询会员(组件弹窗)")
    public PageResponse<CrmMemberListResponseVo> pageSimple(@RequestBody @Validated CrmMemberQueryPageRequestVo request) {
        return crmMemberBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询会员(应用于会员管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:CRM:CUSTOMER:MEMBER:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.CRM,
            moduleEntity = ModuleEntityEnum.CRM_MEMBER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询会员(会员管理菜单)")
    public PageResponse<CrmMemberExpandListResponseVo> pageExpand(@RequestBody @Validated CrmMemberQueryPageRequestVo request) {
        return crmMemberBusiness.pageExpand(request);
    }
}