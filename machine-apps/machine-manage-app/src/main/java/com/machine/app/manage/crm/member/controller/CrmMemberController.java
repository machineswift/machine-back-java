package com.machine.app.manage.crm.member.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.crm.member.business.ICrmMemberBusiness;
import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberDetailResponseVo;
import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberExpandListResponseVo;
import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberListResponseVo;
import com.machine.app.manage.crm.member.controller.vo.resquest.CrmMemberCreateRequestVo;
import com.machine.app.manage.crm.member.controller.vo.resquest.CrmMemberQueryPageRequestVo;
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
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【CRM】会员")
@RestController
@RequestMapping("manage/crm/member")
public class CrmMemberController {

    @Autowired
    private ICrmMemberBusiness crmMemberBusiness;
    
    @Operation(summary = "创建会员")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:MEMBER:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated CrmMemberCreateRequestVo request) {
        log.info("创建会员，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(crmMemberBusiness.create(request));
    }

    @Operation(summary = "删除会员")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:MEMBER:DELETE')")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除会员，request={}", JSONUtil.toJsonStr(request));
        crmMemberBusiness.delete(request);
    }

    @Operation(summary = "修改会员")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:MEMBER:UPDATE')")
    public void update(@RequestBody @Validated CrmMemberUpdateRequestVo request) {
        log.info("修改会员，request={}", JSONUtil.toJsonStr(request));
        crmMemberBusiness.update(request);
    }

    @Operation(summary = "会员详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:MEMBER:DETAIL')")
    public CrmMemberDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return crmMemberBusiness.detail(request);
    }

    @Operation(summary = "分页查询会员(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:MEMBER:PAGE_SIMPLE')")
    public PageResponse<CrmMemberListResponseVo> pageSimple(@RequestBody @Validated CrmMemberQueryPageRequestVo request) {
        return crmMemberBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询会员(应用于会员管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('CRM:CUSTOMER:MEMBER:PAGE_EXPAND')")
    public PageResponse<CrmMemberExpandListResponseVo> pageExpand(@RequestBody @Validated CrmMemberQueryPageRequestVo request) {
        return crmMemberBusiness.pageExpand(request);
    }
}