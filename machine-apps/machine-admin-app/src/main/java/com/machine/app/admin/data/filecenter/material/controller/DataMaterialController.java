package com.machine.app.admin.data.filecenter.material.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.filecenter.material.business.IDataMaterialBusiness;
import com.machine.app.admin.data.filecenter.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialCreateRequestVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialQueryPageRequestVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialUpdateCategoryRequestVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialUpdateRequestVo;
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
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】素材管理")
@RestController
@RequestMapping("admin/data/file_center/material")
public class DataMaterialController {

    @Autowired
    private IDataMaterialBusiness materialBusiness;

    @Operation(summary = "新增")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:MATERIAL:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_MATERIAL,
            operateType = ActionTypeEnum.CREATE,
            operateName = "新增素材",
            moduleEntityId = "#request.title",
            content = "'新增素材：' + #request.title",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated DataMaterialCreateRequestVo request,
                                     HttpServletRequest servletRequest) {
        log.info("新增素材，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(materialBusiness.create(request, servletRequest));
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:MATERIAL:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_MATERIAL,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改素材",
            moduleEntityId = "#request.id",
            content = "'修改素材：' + #request.title")
    public void update(@RequestBody @Validated DataMaterialUpdateRequestVo request,
                       HttpServletRequest servletRequest) {
        log.info("修改素材，request={}", JSONUtil.toJsonStr(request));
        materialBusiness.update(request, servletRequest);
    }

    @Operation(summary = "修改分类")
    @PostMapping("update_category")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:MATERIAL:UPDATE_CATEGORY')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_MATERIAL,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改素材分类",
            moduleEntityId = "#request.id",
            content = "'修改素材分类：' + #request.id")
    public void updateCategory(@RequestBody @Validated DataMaterialUpdateCategoryRequestVo request) {
        log.info("修改素材分类，request={}", JSONUtil.toJsonStr(request));
        materialBusiness.updateCategory(request);
    }

    @Operation(summary = "素材详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:MATERIAL:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_MATERIAL,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询素材详情")
    public DataMaterialDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return materialBusiness.detail(request);
    }

    @Operation(summary = "素材分页列表(管理端)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:MATERIAL:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_MATERIAL,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询素材(管理端)")
    public PageResponse<DataMaterialExpandListResponseVo> pageExpand(@RequestBody @Validated DataMaterialQueryPageRequestVo request) {
        return materialBusiness.pageExpand(request);
    }
}

