package com.machine.app.manage.data.tag.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.tag.business.IDataTagOptionBusiness;
import com.machine.app.manage.data.tag.controller.vo.request.*;
import com.machine.app.manage.data.tag.controller.vo.response.*;
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

import java.util.List;

@Slf4j
@Tag(name = "【DATA】智能标签选项")
@RestController
@RequestMapping("manage/data/tag_option")
public class DataTagOptionController {

    @Autowired
    private IDataTagOptionBusiness tagOptionBusiness;

    @Operation(summary = "创建智能标签选项")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG_OPTION:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated DataTagOptionCreateRequestVo request) {
        log.info("创建智能标签选项，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(tagOptionBusiness.create(request));
    }

    @Operation(summary = "删除智能标签选项")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG_OPTION:DELETE')")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除智能标签选项，request={}", JSONUtil.toJsonStr(request));
        tagOptionBusiness.delete(request);
    }

    @Operation(summary = "修改智能标签选项")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG_OPTION:UPDATE')")
    public void update(@RequestBody @Validated DataTagOptionUpdateRequestVo request) {
        log.info("修改智能标签选项，request={}", JSONUtil.toJsonStr(request));
        tagOptionBusiness.update(request);
    }


    @Operation(summary = "修改智能标签选项编码")
    @PostMapping("update_code")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG_OPTION:UPDATE_CODE')")
    public void updateCode(@RequestBody @Validated DataTagOptionUpdateCodeRequestVo request) {
        log.info("修改智能标签选项编码，request={}", JSONUtil.toJsonStr(request));
        tagOptionBusiness.updateCode(request);
    }

    @Operation(summary = "修改智能标签选项状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG_OPTION:UPDATE_STATUS')")
    public void updateStatus(@RequestBody @Validated DataTagOptionUpdateStatusRequestVo request) {
        log.info("修改智能标签选项状态，request={}", JSONUtil.toJsonStr(request));
        tagOptionBusiness.updateStatus(request);
    }

    @Operation(summary = "修改智能标签选项排序")
    @PostMapping("update_sort")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG_OPTION:UPDATE_SORT')")
    public void updateSort(@RequestBody @Validated DataTagOptionUpdateSortRequestVo request) {
        log.info("修改智能标签选项排序，request={}", JSONUtil.toJsonStr(request));
        tagOptionBusiness.updateSort(request);
    }

    @Operation(summary = "智能标签选项详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG_OPTION:DETAIL')")
    public DataTagOptionDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return tagOptionBusiness.detail(request);
    }

    @Operation(summary = "查询智能标签选项(应用于组件弹窗)")
    @PostMapping("list_simple")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG_OPTION:LIST_SIMPLE')")
    public List<DataTagOptionSimpleListResponseVo> listSimple(@RequestBody @Validated DataTagOptionQueryListRequestVo request) {
        return tagOptionBusiness.listSimple(request);
    }

    @Operation(summary = "查询智能标签列表(选项应用于管理菜单)")
    @PostMapping("list_expand")
    @PreAuthorize("hasAuthority('SYSTEM:BASIC_DATA:TAG_OPTION:LIST_EXPAND')")
    public List<DataTagOptionExpandListResponseVo> listExpand(@RequestBody @Validated DataTagOptionQueryListRequestVo request) {
        return tagOptionBusiness.listExpand(request);
    }
}
