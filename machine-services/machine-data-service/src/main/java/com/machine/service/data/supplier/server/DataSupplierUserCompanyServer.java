package com.machine.service.data.supplier.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.supplier.IDataSupplierCompanyClient;
import com.machine.client.data.supplier.dto.input.*;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanyDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanyListOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanySimpleListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.supplier.service.IDataSupplierCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/data/supplier_company")
public class DataSupplierUserCompanyServer implements IDataSupplierCompanyClient {

    @Autowired
    private IDataSupplierCompanyService supplierCompanyService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataSupplierCompanyCreateInputDto inputDto) {
        log.info("创建供应商公司，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return supplierCompanyService.create(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated DataSupplierCompanyUpdateStatusInputDto inputDto) {
        log.info("修改供应商公司状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return supplierCompanyService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DataSupplierCompanyUpdateInputDto inputDto) {
        log.info("修改供应商公司，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return supplierCompanyService.update(inputDto);
    }

    @Override
    @PostMapping("detail")
    public DataSupplierCompanyDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return supplierCompanyService.detail(request);
    }

    @Override
    @PostMapping("list_by_idSet")
    public List<DataSupplierCompanySimpleListOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request) {
        return supplierCompanyService.listByIdSet(request);
    }

    @Override
    @PostMapping("list_by_supplierId")
    public List<DataSupplierCompanySimpleListOutputDto> listBySupplierId(@RequestBody @Validated IdRequest request) {
        return supplierCompanyService.listBySupplierId(request);
    }

    @Override
    @PostMapping("list_by_offset")
    public List<DataSupplierCompanySimpleListOutputDto> listByOffset(@RequestBody @Validated DataSupplierCompanyQueryListOffsetInputDto inputDto) {
        return supplierCompanyService.listByOffset(inputDto);
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, DataSupplierCompanySimpleListOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        return supplierCompanyService.mapByIdSet(request);
    }

    @Override
    @PostMapping("page_sample")
    public PageResponse<DataSupplierCompanySimpleListOutputDto> pageSample(@RequestBody @Validated DataSupplierCompanySimplePageInputDto inputDto) {
        Page<DataSupplierCompanySimpleListOutputDto> pageResult = supplierCompanyService.pageSample(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("page_expand")
    public PageResponse<DataSupplierCompanyListOutputDto> pageExpand(@RequestBody @Validated DataSupplierCompanyPageInputDto inputDto) {
        Page<DataSupplierCompanyListOutputDto> pageResult = supplierCompanyService.pageExpand(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
