package com.machine.app.admin.scm.property.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.property.business.IScmPropertyValueBusiness;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyValueCreateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyValueListByPropertyRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyValueUpdateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.response.ScmPropertyValueListResponseVo;
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
@RequestMapping("admin/scm/property_value")
public class ScmPropertyValueController {

    @Autowired
    private IScmPropertyValueBusiness propertyValueBusiness;

    @Operation(summary = "创建属性枚举值")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY_VALUE:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_PROPERTY_VALUE,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建属性值",
            moduleEntityId = "#request.value",
            content = "'创建属性值：' + #request.value",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated ScmPropertyValueCreateRequestVo request) {
        log.info("创建属性值，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(propertyValueBusiness.create(request));
    }

    @Operation(summary = "修改属性枚举值")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY_VALUE:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_PROPERTY_VALUE,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改属性值",
            moduleEntityId = "#request.id",
            content = "'修改属性值：' + #request.value")
    public void update(@RequestBody @Validated ScmPropertyValueUpdateRequestVo request) {
        log.info("修改属性值，request={}", JSONUtil.toJsonStr(request));
        propertyValueBusiness.update(request);
    }

    @Operation(summary = "删除属性枚举值")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY_VALUE:DELETE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_PROPERTY_VALUE,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除属性值",
            moduleEntityId = "#request.id",
            content = "'删除属性值：' + #request.id")
    public void deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性值，id={}", request.getId());
        propertyValueBusiness.deleteById(request);
    }

    @Operation(summary = "根据属性ID查询枚举值列表")
    @PostMapping("list_by_property_id")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY_VALUE:LIST')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_PROPERTY_VALUE,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询属性值列表")
    public List<ScmPropertyValueListResponseVo> listByPropertyId(
            @RequestBody @Validated ScmPropertyValueListByPropertyRequestVo request) {
        return propertyValueBusiness.listByPropertyId(request);
    }
}
