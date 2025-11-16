package com.machine.app.manage.data.tag.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.tag.business.IDataTagBusiness;
import com.machine.app.manage.data.tag.controller.vo.request.*;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagDetailResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagExpandListResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagSimpleListResponseVo;
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
@Tag(name = "【DATA】智能标签")
@RestController
@RequestMapping("manage/data/tag")
public class DataTagController {

    @Autowired
    private IDataTagBusiness tagBusiness;

    @Operation(summary = "创建智能标签")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated DataTagCreateRequestVo request) {
        log.info("创建智能标签，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(tagBusiness.create(request));
    }

    @Operation(summary = "删除智能标签")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG:DELETE')")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除智能标签，request={}", JSONUtil.toJsonStr(request));
        tagBusiness.delete(request);
    }

    @Operation(summary = "修改智能标签")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG:UPDATE')")
    public void update(@RequestBody @Validated DataTagUpdateRequestVo request) {
        log.info("修改智能标签，request={}", JSONUtil.toJsonStr(request));
        tagBusiness.update(request);
    }

    @Operation(summary = "修改智能标签编码")
    @PostMapping("update_code")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG:UPDATE_CODE')")
    public void updateCode(@RequestBody @Validated DataTagUpdateCodeRequestVo request) {
        log.info("修改智能标签编码，request={}", JSONUtil.toJsonStr(request));
        tagBusiness.updateCode(request);
    }

    @Operation(summary = "修改智能标签状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG:UPDATE_STATUS')")
    public void updateStatus(@RequestBody @Validated DataTagUpdateStatusRequestVo request) {
        log.info("修改智能标签状态，request={}", JSONUtil.toJsonStr(request));
        tagBusiness.updateStatus(request);
    }

    @Operation(summary = "修改智能标签排序")
    @PostMapping("update_sort")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG:UPDATE_SORT')")
    public void updateSort(@RequestBody @Validated DataTagUpdateSortRequestVo request) {
        log.info("修改智能标签排序，request={}", JSONUtil.toJsonStr(request));
        tagBusiness.updateSort(request);
    }

    @Operation(summary = "修改智能标签关联分类")
    @PostMapping("update_category")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG:UPDATE_CATEGORY')")
    public void updateCategory(@RequestBody @Validated DataTagUpdateCategoryRequestVo request) {
        log.info("修改智能标签关联分类，request={}", JSONUtil.toJsonStr(request));
        tagBusiness.updateCategory(request);
    }

    @Operation(summary = "智能标签详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG:DETAIL')")
    public DataTagDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return tagBusiness.detail(request);
    }

    @Operation(summary = "分页查询智能标签(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG:PAGE_SIMPLE')")
    public PageResponse<DataTagSimpleListResponseVo> pageSimple(@RequestBody @Validated DataTagQueryPageRequestVo request) {
        return tagBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询智能标签(应用于管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG:PAGE_EXPAND')")
    public PageResponse<DataTagExpandListResponseVo> pageExpand(@RequestBody @Validated DataTagQueryPageRequestVo request) {
        return tagBusiness.pageExpand(request);
    }
}
