package com.machine.service.data.employee.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.employee.IShopEmployeeClient;
import com.machine.client.data.employee.dto.input.*;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.employee.service.IShopEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/shop_employee")
public class ShopEmployeeServer implements IShopEmployeeClient {

    @Autowired
    private IShopEmployeeService shopEmployeeService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataShopEmployeeCreateInputDto inputDto) {
        log.info("新增门店员工数据，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopEmployeeService.create(inputDto);
    }

    @Override
    @PostMapping("update_by_userId")
    public int updateByUserId(@RequestBody @Validated DataShopEmployeeUpdateInputDto inputDto) {
        log.info("修改门店员工数据，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopEmployeeService.updateByUserId(inputDto);
    }

    @Override
    @PostMapping("detail")
    public ShopEmployeeDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return shopEmployeeService.detail(request);
    }

    @Override
    @PostMapping("get_by_userId")
    public ShopEmployeeDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request) {
        return shopEmployeeService.getByUserId(request);
    }

    @Override
    @PostMapping("identity_card")
    public OpenapiShopEmployeeIdentityCardOutputDto identityCard(@RequestBody @Validated IdRequest request) {
        return shopEmployeeService.identityCard(request);
    }

    @Override
    @PostMapping("health_certificate")
    public OpenapiShopEmployeeHealthCertificateOutputDto healthCertificate(@RequestBody @Validated IdRequest request) {
        return shopEmployeeService.healthCertificate(request);
    }

    @Override
    @PostMapping("list_by_offset")
    public List<ShopEmployeeListOutputDto> listByOffset(@RequestBody @Validated DataShopEmployeeQueryListOffsetInputDto inputDto) {
        return shopEmployeeService.listByOffset(inputDto);
    }
}
