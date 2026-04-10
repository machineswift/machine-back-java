package com.machine.app.manage.data.file.material.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.file.material.business.IDataMaterialBusiness;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCreateRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialQueryPageRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialUpdateCategoryRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialUpdateRequestVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】素材管理")
@RestController
@RequestMapping("manage/data/file/material")
public class DataMaterialController {

    @Autowired
    private IDataMaterialBusiness materialBusiness;

    @Operation(summary = "新增")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:MATERIAL:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated DataMaterialCreateRequestVo request) {
        log.info("新增素材，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(materialBusiness.create(request));
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:MATERIAL:UPDATE')")
    public void update(@RequestBody @Validated DataMaterialUpdateRequestVo request) {
        log.info("修改素材，request={}", JSONUtil.toJsonStr(request));
        materialBusiness.update(request);
    }

    @Operation(summary = "修改分类")
    @PostMapping("update_category")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:MATERIAL:UPDATE_CATEGORY')")
    public void updateCategory(@RequestBody @Validated DataMaterialUpdateCategoryRequestVo request) {
        log.info("修改素材分类，request={}", JSONUtil.toJsonStr(request));
        materialBusiness.updateCategory(request);
    }

    @Operation(summary = "素材详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:MATERIAL:DETAIL')")
    public DataMaterialDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return materialBusiness.detail(request);
    }

    @Operation(summary = "素材分页列表(管理端)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:MATERIAL:PAGE_EXPAND')")
    public PageResponse<DataMaterialExpandListResponseVo> pageExpand(@RequestBody @Validated DataMaterialQueryPageRequestVo request) {
        return materialBusiness.pageExpand(request);
    }
}

