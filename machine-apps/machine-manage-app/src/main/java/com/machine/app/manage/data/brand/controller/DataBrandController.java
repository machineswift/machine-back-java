package com.machine.app.manage.data.brand.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.brand.business.IDataBrandBusiness;
import com.machine.app.manage.data.brand.controller.vo.request.DataBrandCreateRequestVo;
import com.machine.app.manage.data.brand.controller.vo.request.DataBrandQueryPageRequestVo;
import com.machine.app.manage.data.brand.controller.vo.request.DataBrandUpdateRequestVo;
import com.machine.app.manage.data.brand.controller.vo.request.DataBrandUpdateStatusRequestVo;
import com.machine.app.manage.data.brand.controller.vo.response.DataBrandDetailResponseVo;
import com.machine.app.manage.data.brand.controller.vo.response.DataBrandExpandListResponseVo;
import com.machine.app.manage.data.brand.controller.vo.response.DataBrandSimpleListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】品牌模块")
@RestController
@RequestMapping("manage/data/brand")
public class DataBrandController {

    @Autowired
    private IDataBrandBusiness brandBusiness;

    @Operation(summary = "创建品牌")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:BRAND:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated DataBrandCreateRequestVo request) {
        log.info("创建品牌，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(brandBusiness.create(request));
    }

    @Operation(summary = "删除品牌")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:BRAND:DELETE')")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除品牌，request={}", JSONUtil.toJsonStr(request));
        brandBusiness.delete(request);
    }

    @Operation(summary = "修改品牌")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:BRAND:UPDATE')")
    public void update(@RequestBody @Validated DataBrandUpdateRequestVo request) {
        log.info("修改品牌，request={}", JSONUtil.toJsonStr(request));
        brandBusiness.update(request);
    }

    @Operation(summary = "修改品牌状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:BRAND:UPDATE_STATUS')")
    public void updateStatus(@RequestBody @Validated DataBrandUpdateStatusRequestVo request) {
        log.info("修改品牌状态，request={}", JSONUtil.toJsonStr(request));
        brandBusiness.updateStatus(request);
    }

    @Operation(summary = "品牌详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:BRAND:DETAIL')")
    public DataBrandDetailResponseVo detail(@RequestBody IdRequest request) {
        return brandBusiness.detail(request);
    }

    @Operation(summary = "分页查询品牌(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:BRAND:PAGE_SIMPLE')")
    public PageResponse<DataBrandSimpleListResponseVo> pageSimple(@RequestBody @Validated DataBrandQueryPageRequestVo request) {
        return brandBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询品牌(应用于管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:BRAND:PAGE_EXPAND')")
    public PageResponse<DataBrandExpandListResponseVo> pageExpand(@RequestBody @Validated DataBrandQueryPageRequestVo request) {
        return brandBusiness.pageExpand(request);
    }

}

