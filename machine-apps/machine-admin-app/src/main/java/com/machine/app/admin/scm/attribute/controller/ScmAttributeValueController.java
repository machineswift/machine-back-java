package com.machine.app.admin.scm.attribute.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.attribute.business.IScmAttributeValueBusiness;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueCreateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueListByAttributeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueUpdateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueUpdateCodeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueUpdateStatusRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeValueListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeValueSimpleListResponseVo;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "【SCM】属性值")
@RestController
@RequestMapping("admin/scm/attribute_value")
public class ScmAttributeValueController {

    @Autowired
    private IScmAttributeValueBusiness attributeValueBusiness;

    @Operation(summary = "创建属性枚举值")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_VALUE:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_VALUE,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建属性值",
            moduleEntityId = "#request.value",
            content = "'创建属性值：' + #request.value",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated ScmAttributeValueCreateRequestVo request) {
        log.info("创建属性值，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(attributeValueBusiness.create(request));
    }

    @Operation(summary = "删除属性值")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_VALUE:DELETE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_VALUE,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除属性值",
            moduleEntityId = "#request.id",
            content = "'删除属性值：' + #request.id")
    public void deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性值，id={}", request.getId());
        attributeValueBusiness.deleteById(request);
    }

    @Operation(summary = "修改属性枚举值")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_VALUE:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_VALUE,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性值",
            moduleEntityId = "#request.id",
            content = "'修改属性值：' + #request.name")
    public void update(@RequestBody @Validated ScmAttributeValueUpdateRequestVo request) {
        log.info("修改属性值，request={}", JSONUtil.toJsonStr(request));
        attributeValueBusiness.update(request);
    }

    @Operation(summary = "修改属性值状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_VALUE:UPDATE_STATUS')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_VALUE,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性值状态",
            moduleEntityId = "#request.id",
            content = "'修改属性值状态：' + #request.id",
            diff = false)
    public void updateStatus(@RequestBody @Validated ScmAttributeValueUpdateStatusRequestVo request) {
        log.info("修改属性值状态，request={}", JSONUtil.toJsonStr(request));
        attributeValueBusiness.updateStatus(request);
    }

    @Operation(summary = "修改属性值编码")
    @PostMapping("update_code")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_VALUE:UPDATE_CODE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_VALUE,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性值编码",
            moduleEntityId = "#request.id",
            content = "'修改属性值编码：' + #request.code",
            diff = false)
    public void updateCode(@RequestBody @Validated ScmAttributeValueUpdateCodeRequestVo request) {
        log.info("修改属性值编码，request={}", JSONUtil.toJsonStr(request));
        attributeValueBusiness.updateCode(request);
    }

    @Operation(summary = "根据属性ID查询属性值列表（应用于组件弹窗）")
    @PostMapping("list_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_VALUE:LIST_SIMPLE')")
    public List<ScmAttributeValueSimpleListResponseVo> listSimple(
            @RequestBody @Validated ScmAttributeValueListByAttributeRequestVo request) {
        return attributeValueBusiness.listSimple(request);
    }

    @Operation(summary = "根据属性ID查询属性值列表（应用于管理菜单）")
    @PostMapping("list_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:ATTRIBUTE_VALUE:LIST_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_ATTRIBUTE_VALUE,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询属性值列表(属性库管理菜单)")
    public List<ScmAttributeValueListResponseVo> listExpand(
            @RequestBody @Validated ScmAttributeValueListByAttributeRequestVo request) {
        return attributeValueBusiness.listExpand(request);
    }
}
