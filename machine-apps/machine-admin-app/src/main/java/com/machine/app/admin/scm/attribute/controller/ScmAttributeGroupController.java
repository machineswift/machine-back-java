package com.machine.app.admin.scm.attribute.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.attribute.business.IScmAttributeGroupBusiness;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupCreateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupQueryListRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupQueryPageRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupUpdateAttributeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupUpdateBackCategoryRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupUpdateCodeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupUpdateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupUpdateStatusRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupDetailResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupRelationListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupSimpleListResponseVo;
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

import java.util.List;

@Slf4j
@Tag(name = "【SCM】属性分组")
@RestController
@RequestMapping("admin/scm/attribute_group")
public class ScmAttributeGroupController {

    @Autowired
    private IScmAttributeGroupBusiness attributeGroupBusiness;

    @Operation(summary = "创建属性分组")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_GROUP:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_GROUP,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建属性分组",
            moduleEntityId = "#request.name",
            content = "'创建属性分组：' + #request.name",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated ScmAttributeGroupCreateRequestVo request) {
        log.info("创建属性分组，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(attributeGroupBusiness.create(request));
    }

    @Operation(summary = "删除属性分组")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_GROUP:DELETE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_GROUP,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除属性分组",
            moduleEntityId = "#request.id",
            content = "'删除属性分组：' + #request.id")
    public void deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性分组，id={}", request.getId());
        attributeGroupBusiness.deleteById(request);
    }

    @Operation(summary = "修改属性分组")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_GROUP:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_GROUP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性分组",
            moduleEntityId = "#request.id",
            content = "'修改属性分组：' + #request.name")
    public void update(@RequestBody @Validated ScmAttributeGroupUpdateRequestVo request) {
        log.info("修改属性分组，request={}", JSONUtil.toJsonStr(request));
        attributeGroupBusiness.update(request);
    }

    @Operation(summary = "修改属性分组状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_GROUP:UPDATE_STATUS')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_GROUP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性分组状态",
            moduleEntityId = "#request.id",
            content = "'修改属性分组状态：' + #request.id",
            diff = false)
    public void updateStatus(@RequestBody @Validated ScmAttributeGroupUpdateStatusRequestVo request) {
        log.info("修改属性分组状态，request={}", JSONUtil.toJsonStr(request));
        attributeGroupBusiness.updateStatus(request);
    }

    @Operation(summary = "修改属性分组编码")
    @PostMapping("update_code")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_GROUP:UPDATE_CODE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_GROUP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性分组编码",
            moduleEntityId = "#request.id",
            content = "'修改属性分组编码：' + #request.code",
            diff = false)
    public void updateCode(@RequestBody @Validated ScmAttributeGroupUpdateCodeRequestVo request) {
        log.info("修改属性分组编码，request={}", JSONUtil.toJsonStr(request));
        attributeGroupBusiness.updateCode(request);
    }

    @Operation(summary = "修改属性分组关联的后台分类")
    @PostMapping("update_back_category")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_GROUP:UPDATE_BACK_CATEGORY')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_GROUP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性分组关联后台分类",
            moduleEntityId = "#request.id",
            content = "'修改属性分组关联后台分类：' + #request.id")
    public void updateBackCategory(@RequestBody @Validated ScmAttributeGroupUpdateBackCategoryRequestVo request) {
        log.info("修改属性分组关联后台分类，request={}", JSONUtil.toJsonStr(request));
        attributeGroupBusiness.updateBackCategory(request);
    }

    @Operation(summary = "修改属性分组关联的属性")
    @PostMapping("update_attribute")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_GROUP:UPDATE_ATTRIBUTE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_GROUP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性分组关联属性",
            moduleEntityId = "#request.id",
            content = "'修改属性分组关联属性：' + #request.id")
    public void updateAttribute(@RequestBody @Validated ScmAttributeGroupUpdateAttributeRequestVo request) {
        log.info("修改属性分组关联属性，request={}", JSONUtil.toJsonStr(request));
        attributeGroupBusiness.updateAttribute(request);
    }

    @Operation(summary = "查询属性分组详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_GROUP:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_GROUP,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询属性分组详情")
    public ScmAttributeGroupDetailResponseVo getById(@RequestBody @Valid IdRequest request) {
        return attributeGroupBusiness.getById(request);
    }

    @Operation(summary = "查询属性分组列表(应用于组件弹窗, 含分组在当前后台分类下的关联配置)")
    @PostMapping("list_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_GROUP:LIST_SIMPLE')")
    public List<ScmAttributeGroupRelationListResponseVo> listSimple(
            @RequestBody @Validated ScmAttributeGroupQueryListRequestVo request) {
        return attributeGroupBusiness.listSimple(request);
    }

    @Operation(summary = "分页查询属性分组（应用于组件弹窗）")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_GROUP:PAGE_SIMPLE')")
    public PageResponse<ScmAttributeGroupSimpleListResponseVo> pageSimple(
            @RequestBody @Validated ScmAttributeGroupQueryPageRequestVo request) {
        return attributeGroupBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询属性分组（应用于属性分组管理菜单）")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_GROUP:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_GROUP,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询属性分组(属性分组管理菜单)")
    public PageResponse<ScmAttributeGroupListResponseVo> pageExpand(
            @RequestBody @Validated ScmAttributeGroupQueryPageRequestVo request) {
        return attributeGroupBusiness.pageExpand(request);
    }

}
