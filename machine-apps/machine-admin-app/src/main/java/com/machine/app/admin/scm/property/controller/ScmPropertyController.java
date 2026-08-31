package com.machine.app.admin.scm.property.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.property.business.IScmPropertyBusiness;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyCreateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyQueryPageRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyUpdateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.response.ScmPropertyDetailResponseVo;
import com.machine.app.admin.scm.property.controller.vo.response.ScmPropertyListResponseVo;
import com.machine.app.admin.scm.property.controller.vo.response.ScmPropertySimpleListResponseVo;
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
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【SCM】属性库")
@RestController
@RequestMapping("admin/scm/property")
public class ScmPropertyController {

    @Autowired
    private IScmPropertyBusiness propertyBusiness;

    @Operation(summary = "创建属性")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY:CREATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_PROPERTY,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建属性",
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated ScmPropertyCreateRequestVo request) {
        log.info("创建属性，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(propertyBusiness.create(request));
    }

    @Operation(summary = "修改属性")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY:UPDATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_PROPERTY,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性")
    public void update(@RequestBody @Validated ScmPropertyUpdateRequestVo request) {
        log.info("修改属性，request={}", JSONUtil.toJsonStr(request));
        propertyBusiness.update(request);
    }

    @Operation(summary = "删除属性")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY:DELETE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_PROPERTY,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除属性")
    public void deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性，id={}", request.getId());
        propertyBusiness.deleteById(request);
    }

    @Operation(summary = "查询属性详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_PROPERTY,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询属性详情")
    public ScmPropertyDetailResponseVo getById(@RequestBody @Valid IdRequest request) {
        return propertyBusiness.getById(request);
    }

    @Operation(summary = "分页查询属性（应用于组件弹窗/属性选择器）")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY:PAGE_SIMPLE')")
    public PageResponse<ScmPropertySimpleListResponseVo> pageSimple(
            @RequestBody @Validated ScmPropertyQueryPageRequestVo request) {
        return propertyBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询属性（应用于属性库管理菜单）")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_PROPERTY,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询属性(属性库管理菜单)")
    public PageResponse<ScmPropertyListResponseVo> pageExpand(@RequestBody @Validated ScmPropertyQueryPageRequestVo request) {
        return propertyBusiness.pageExpand(request);
    }

}
