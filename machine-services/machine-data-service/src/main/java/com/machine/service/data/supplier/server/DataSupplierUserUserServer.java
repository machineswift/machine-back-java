package com.machine.service.data.supplier.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.supplier.IDataSupplierUserClient;
import com.machine.client.data.supplier.dto.input.DataSupplierCreateInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierListUserIdInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierUpdateInputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.supplier.service.IDataSupplierUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("server/data/supplier_user")
public class DataSupplierUserUserServer implements IDataSupplierUserClient {

    @Autowired
    private IDataSupplierUserService supplierUserService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataSupplierCreateInputDto inputDto) {
        log.info("创建门供应商用户，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return supplierUserService.create(inputDto);
    }

    @Override
    @PostMapping("update")
    public int update(DataSupplierUpdateInputDto inputDto) {
        log.info("修改供应商用户，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return supplierUserService.update(inputDto);
    }

    @Override
    @PostMapping("detail")
    public DataSupplierDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return supplierUserService.detail(request);
    }

    @Override
    @PostMapping("get_by_userId")
    public DataSupplierDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request) {
        return supplierUserService.getByUserId(request);
    }

    @Override
    @PostMapping("list_userId_by_condition")
    public Set<String> listUserIdByCondition(@RequestBody @Validated DataSupplierListUserIdInputDto inputDto) {
        return supplierUserService.listUserIdByCondition(inputDto);
    }

    @Override
    @PostMapping("list_by_offset")
    public List<DataSupplierListOutputDto> listByOffset(@RequestBody @Validated DataSupplierQueryListOffsetInputDto inputDto) {
        return supplierUserService.listByOffset(inputDto);
    }
}
