package com.machine.app.admin.data.area.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.area.business.IDataAreaBusiness;
import com.machine.app.admin.data.area.controller.vo.request.DataAreaCreateRequestVo;
import com.machine.app.admin.data.area.controller.vo.request.DataAreaTreeRequestVo;
import com.machine.app.admin.data.area.controller.vo.request.DataAreaUpdateParentRequestVo;
import com.machine.app.admin.data.area.controller.vo.request.DataAreaUpdateRequestVo;
import com.machine.app.admin.data.area.controller.vo.response.DataAreaTreeExpandResponseVo;
import com.machine.app.admin.data.area.controller.vo.response.DataAreaTreeSimpleResponseVo;
import com.machine.app.admin.data.area.controller.vo.response.DataAreaDetailResponseVo;
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
@Tag(name = "【DATA】区域模块（省市区）")
@RestController
@RequestMapping("admin/data/area")
public class DataAreaController {

    @Autowired
    private IDataAreaBusiness areaBusiness;

    @Operation(summary = "创建区域")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:AREA:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_AREA,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建区域",
            moduleEntityId = "#request.name",
            content = "'创建区域：' + #request.name",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated DataAreaCreateRequestVo request) {
        log.info("创建区域，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(areaBusiness.create(request));
    }

    @Operation(summary = "删除区域")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:AREA:DELETE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_AREA,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除区域",
            moduleEntityId = "#request.id",
            content = "'删除区域：' + #request.id")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除区域，request={}", JSONUtil.toJsonStr(request));
        areaBusiness.delete(request);
    }

    @Operation(summary = "修改区域")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:AREA:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_AREA,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改区域",
            moduleEntityId = "#request.id",
            content = "'修改区域：' + #request.name")
    public void update(@RequestBody @Validated DataAreaUpdateRequestVo request) {
        log.info("修改区域，request={}", JSONUtil.toJsonStr(request));
        areaBusiness.update(request);
    }

    @Operation(summary = "修改父区域ID")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:AREA:UPDATE_PARENT')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_AREA,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改区域父节点",
            moduleEntityId = "#request.id",
            content = "'修改区域父节点：' + #request.id",
            diff = false)
    public void updateParent(@RequestBody @Validated DataAreaUpdateParentRequestVo request) {
        log.info("修改父区域，request={}", JSONUtil.toJsonStr(request));
        areaBusiness.updateParent(request);
    }

    @Operation(summary = "区域详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:AREA:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_AREA,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询区域详情")
    public DataAreaDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return areaBusiness.detail(request);
    }

    @Operation(summary = "区域树(应用于组件弹窗)")
    @PostMapping("tree_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:AREA:TREE_SIMPLE')")
    public DataAreaTreeSimpleResponseVo treeSimple(@RequestBody @Validated DataAreaTreeRequestVo request) {
        return areaBusiness.treeSimple(request);
    }

    @Operation(summary = "区域树(应用于区域管理菜单)")
    @PostMapping("tree_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:AREA:TREE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_AREA,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询区域树(区域管理菜单)")
    public DataAreaTreeExpandResponseVo treeExpand(@RequestBody @Validated DataAreaTreeRequestVo request) {
        return areaBusiness.treeExpand(request);
    }
}