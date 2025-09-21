package com.machine.app.manage.data.material.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.material.business.IDataMaterialCategoryBusiness;
import com.machine.app.manage.data.material.controller.vo.response.DataMaterialCategoryDetailResponseVo;
import com.machine.app.manage.data.material.controller.vo.response.DataMaterialCategorySimpleTreeResponseVo;
import com.machine.app.manage.data.material.controller.vo.resquest.DataMaterialCategoryCreateRequestVo;
import com.machine.app.manage.data.material.controller.vo.resquest.DataMaterialCategoryUpdateParentRequestVo;
import com.machine.app.manage.data.material.controller.vo.resquest.DataMaterialCategoryUpdateRequestVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】素材分类模块")
@RestController
@RequestMapping("manage/data/material_category")
public class DataMaterialCategoryController {

    @Autowired
    private IDataMaterialCategoryBusiness materialCategoryBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    public IdResponse<String> create(@RequestBody @Validated DataMaterialCategoryCreateRequestVo request) {
        log.info("创建素材分类，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(materialCategoryBusiness.create(request));
    }

    @Operation(summary = "删除")
    @PostMapping("delete")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除素材分类，request={}", JSONUtil.toJsonStr(request));
        materialCategoryBusiness.delete(request);
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    public void update(@RequestBody @Validated DataMaterialCategoryUpdateRequestVo request) {
        log.info("修改素材分类，request={}", JSONUtil.toJsonStr(request));
        materialCategoryBusiness.update(request);
    }

    @Operation(summary = "修改父ID")
    @PostMapping("update_parent")
    public void updateParent(@RequestBody @Validated DataMaterialCategoryUpdateParentRequestVo request) {
        log.info("修改父素材分类，request={}", JSONUtil.toJsonStr(request));
        materialCategoryBusiness.updateParent(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    public DataMaterialCategoryDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return materialCategoryBusiness.detail(request);
    }

    @Operation(summary = "树(应用于组件弹窗)")
    @GetMapping("tree_simple")
    public DataMaterialCategorySimpleTreeResponseVo treeAllSimple() {
        return materialCategoryBusiness.treeAllSimple();
    }
}