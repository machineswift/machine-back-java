package com.machine.service.data.franchisee.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.franchisee.IDataFranchiseeClient;
import com.machine.client.data.franchisee.dto.input.*;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeDetailOutputDto;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeListOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeHealthCertificateOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeIdentityCardOutputDto;
import com.machine.client.data.supplier.dto.input.DataFranchiseeListUserIdInputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.franchisee.service.IDataFranchiseeService;
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
@RequestMapping("server/data/franchisee")
public class DataFranchiseeServer implements IDataFranchiseeClient {

    @Autowired
    private IDataFranchiseeService franchiseeService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataFranchiseeCreateInputDto inputDto) {
        log.info("创建加盟商，inputDto:{}", JSONUtil.toJsonStr(inputDto));
        return franchiseeService.create(inputDto);
    }

    @Override
    @PostMapping("bind_shop")
    public boolean bindShop(@RequestBody @Validated DataFranchiseeBindShopInputDto inputDto) {
        log.info("加盟商绑定门店，inputDto:{}", JSONUtil.toJsonStr(inputDto));
        return franchiseeService.bindShop(inputDto);
    }

    @Override
    @PostMapping("unbind_shop")
    public boolean unbindShop(@RequestBody @Validated DataFranchiseeUnbindShopInputDto inputDto) {
        log.info("加盟商解绑门店，inputDto:{}", JSONUtil.toJsonStr(inputDto));
        return franchiseeService.unbindShop(inputDto);
    }

    @Override
    @PostMapping("update_phone")
    public int updatePhone(@RequestBody @Validated OpenApiFranchiseeUpdatePhoneInputDto inputDto) {
        log.info("修改加盟商手机号，inputDto:{}", JSONUtil.toJsonStr(inputDto));
        return franchiseeService.updatePhone(inputDto);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DataFranchiseeUpdateInputDto inputDto) {
        log.info("修改加盟商，inputDto:{}", JSONUtil.toJsonStr(inputDto));
        return franchiseeService.update(inputDto);
    }

    @Override
    @PostMapping("detail")
    public DataFranchiseeDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return franchiseeService.detail(request);
    }

    @Override
    @PostMapping("detail_by_userId")
    public DataFranchiseeDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request) {
        return franchiseeService.getByUserId(request);
    }

    @Override
    @PostMapping("detail_by_code")
    public DataFranchiseeDetailOutputDto detailByCode(@RequestBody @Validated IdRequest request) {
        return franchiseeService.detailByCode(request);
    }

    @Override
    @PostMapping("identity_card")
    public OpenapiFranchiseeIdentityCardOutputDto identityCard(@RequestBody @Validated IdRequest request) {
        return franchiseeService.identityCard(request);
    }

    @Override
    @PostMapping("health_certificate")
    public OpenapiFranchiseeHealthCertificateOutputDto healthCertificate(@RequestBody @Validated IdRequest request) {
        return franchiseeService.healthCertificate(request);
    }

    @Override
    @PostMapping("list_userId_by_condition")
    public Set<String> listUserIdByCondition(@RequestBody @Validated DataFranchiseeListUserIdInputDto inputDto) {
        return franchiseeService.listUserIdByCondition(inputDto);
    }

    @Override
    @PostMapping("list_by_offset")
    public List<DataFranchiseeListOutputDto> listByOffset(@RequestBody @Validated DataFranchiseeQueryListOffsetInputDto inputDto) {
        return franchiseeService.listByOffset(inputDto);
    }
}
