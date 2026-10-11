package com.machine.app.admin.scm.attribute.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.attribute.business.IScmAttributeBusiness;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeCreateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeQueryPageRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateAttributeGroupRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateBackCategoryRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateCodeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeDetailResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import com.machine.starter.web.operateLog.annotation.WebOperationLog;
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
@RequestMapping("admin/scm/attribute")
public class ScmAttributeController {

    @Autowired
    private IScmAttributeBusiness attributeBusiness;

    @Operation(summary = "创建属性")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建属性",
            moduleEntityId = "#request.name",
            content = "'创建属性：' + #request.name",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated ScmAttributeCreateRequestVo request) {
        log.info("创建属性，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(attributeBusiness.create(request));
    }

    @Operation(summary = "删除属性")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE:DELETE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除属性",
            moduleEntityId = "#request.id",
            content = "'删除属性：' + #request.id")
    public void deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性，id={}", request.getId());
        attributeBusiness.deleteById(request);
    }

    @Operation(summary = "修改属性")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性",
            moduleEntityId = "#request.id",
            content = "'修改属性：' + #request.name")
    public void update(@RequestBody @Validated ScmAttributeUpdateRequestVo request) {
        log.info("修改属性，request={}", JSONUtil.toJsonStr(request));
        attributeBusiness.update(request);
    }

    @Operation(summary = "修改属性编码")
    @PostMapping("update_code")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE:UPDATE_CODE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性编码",
            moduleEntityId = "#request.id",
            content = "'修改属性编码：' + #request.code",
            diff = false)
    public void updateCode(@RequestBody @Validated ScmAttributeUpdateCodeRequestVo request) {
        log.info("修改属性编码，request={}", JSONUtil.toJsonStr(request));
        attributeBusiness.updateCode(request);
    }

    @Operation(summary = "修改属性关联的属性分组")
    @PostMapping("update_attribute_group")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE:UPDATE_ATTRIBUTE_GROUP')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性关联属性分组",
            moduleEntityId = "#request.id",
            content = "'修改属性关联属性分组：' + #request.id")
    public void updateAttributeGroup(@RequestBody @Validated ScmAttributeUpdateAttributeGroupRequestVo request) {
        log.info("修改属性关联的属性分组，request={}", JSONUtil.toJsonStr(request));
        attributeBusiness.updateAttributeGroup(request);
    }

    @Operation(summary = "修改属性关联的后台分类")
    @PostMapping("update_back_category")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE:UPDATE_BACK_CATEGORY')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性关联后台分类",
            moduleEntityId = "#request.id",
            content = "'修改属性关联后台分类：' + #request.id")
    public void updateBackCategory(@RequestBody @Validated ScmAttributeUpdateBackCategoryRequestVo request) {
        log.info("修改属性关联后台分类，request={}", JSONUtil.toJsonStr(request));
        attributeBusiness.updateBackCategory(request);
    }

    @Operation(summary = "查询属性详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询属性详情")
    public ScmAttributeDetailResponseVo getById(@RequestBody @Valid IdRequest request) {
        return attributeBusiness.getById(request);
    }

    @Operation(summary = "分页查询属性（应用于组件弹窗）")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE:PAGE_SIMPLE')")
    public PageResponse<ScmAttributeSimpleListResponseVo> pageSimple(
            @RequestBody @Validated ScmAttributeQueryPageRequestVo request) {
        return attributeBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询属性（应用于菜单管理）")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询属性(属性库管理菜单)")
    public PageResponse<ScmAttributeListResponseVo> pageExpand(@RequestBody @Validated ScmAttributeQueryPageRequestVo request) {
        return attributeBusiness.pageExpand(request);
    }

}
