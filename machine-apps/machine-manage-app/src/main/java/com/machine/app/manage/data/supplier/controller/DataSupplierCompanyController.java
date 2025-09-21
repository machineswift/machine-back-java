package com.machine.app.manage.data.supplier.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.supplier.business.IDataSupplierCompanyBusiness;
import com.machine.app.manage.data.supplier.controller.vo.request.*;
import com.machine.app.manage.data.supplier.controller.vo.response.DataSupplierCompanyDetailResponseVo;
import com.machine.app.manage.data.supplier.controller.vo.response.DataSupplierCompanyExpandListResponseVo;
import com.machine.app.manage.data.supplier.controller.vo.response.DataSupplierCompanySimpleListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【DATA】供应商公司模块")
@RestController
@RequestMapping("manage/data/supplier_company")
public class DataSupplierCompanyController {

    @Autowired
    private IDataSupplierCompanyBusiness supplierCompanyBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    public IdResponse<String> create(@RequestBody @Validated DataSupplierCompanyCreateRequestVo request) {
        log.info("创建供应商公司，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(supplierCompanyBusiness.create(request));
    }

    @Operation(summary = "修改状态")
    @PostMapping("update_status")
    public void updateStatus(@RequestBody @Validated DataSupplierCompanyUpdateStatusRequestVo request) {
        log.info("修改供应商公司状态，request={}", JSONUtil.toJsonStr(request));
        supplierCompanyBusiness.updateStatus(request);
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    public void update(@RequestBody @Validated DataSupplierCompanyUpdateRequestVo request) {
        log.info("修改供应商公司，request={}", JSONUtil.toJsonStr(request));
        supplierCompanyBusiness.update(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    public DataSupplierCompanyDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return supplierCompanyBusiness.detail(request);
    }

    @Operation(summary = "分页查询(应用于组件弹窗)")
    @PostMapping("page_simple")
    public PageResponse<DataSupplierCompanySimpleListResponseVo> pageSimple(
            @RequestBody @Validated DataSupplierCompanyQueryPageSimpleRequestVo request) {
        return supplierCompanyBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询(扩充，应用于加盟商公司管理菜单)")
    @PostMapping("page_expand")
    public PageResponse<DataSupplierCompanyExpandListResponseVo> pageExpand(
            @RequestBody @Validated DataSupplierCompanyQueryPageExpandRequestVo request) {
        return supplierCompanyBusiness.pageExpand(request);
    }

}