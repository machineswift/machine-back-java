package com.machine.app.manage.data.file.material.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.file.material.business.IDataMaterialCategoryBusiness;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialCategoryDetailResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialCategorySimpleTreeResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCategoryCreateRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCategoryUpdateParentRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCategoryUpdateRequestVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】素材分类管理")
@RestController
@RequestMapping("manage/data/file/material_category")
public class DataMaterialCategoryController {

    @Autowired
    private IDataMaterialCategoryBusiness materialCategoryBusiness;

    @Operation(summary = "新增")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:MATERIAL_CATEGORY:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated DataMaterialCategoryCreateRequestVo request) {
        log.info("新增素材分类，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(materialCategoryBusiness.create(request));
    }

    @Operation(summary = "删除")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:MATERIAL_CATEGORY:DELETE')")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除素材分类，request={}", JSONUtil.toJsonStr(request));
        materialCategoryBusiness.delete(request);
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:MATERIAL_CATEGORY:UPDATE')")
    public void update(@RequestBody @Validated DataMaterialCategoryUpdateRequestVo request) {
        log.info("修改素材分类，request={}", JSONUtil.toJsonStr(request));
        materialCategoryBusiness.update(request);
    }

    @Operation(summary = "修改父ID")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:MATERIAL_CATEGORY:UPDATE_PARENT')")
    public void updateParent(@RequestBody @Validated DataMaterialCategoryUpdateParentRequestVo request) {
        log.info("修改父素材分类，request={}", JSONUtil.toJsonStr(request));
        materialCategoryBusiness.updateParent(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:MATERIAL_CATEGORY:DETAIL')")
    public DataMaterialCategoryDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return materialCategoryBusiness.detail(request);
    }

    @Operation(summary = "分类树(选择器)", description = "树形结构，用于弹窗选择素材分类")
    @GetMapping("tree_simple")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:MATERIAL_CATEGORY:TREE_SIMPLE')")
    public DataMaterialCategorySimpleTreeResponseVo treeAllSimple() {
        return materialCategoryBusiness.treeAllSimple();
    }
}