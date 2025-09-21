package com.machine.service.crm.customer.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.customer.ICrmCustomerClient;
import com.machine.client.crm.customer.dto.input.CrmCustomerCreateInputDto;
import com.machine.client.crm.customer.dto.input.CrmCustomerQueryPageInputDto;
import com.machine.client.crm.customer.dto.input.CrmCustomerUpdateInputDto;
import com.machine.client.crm.customer.dto.output.CrmCustomerDetailOutputDto;
import com.machine.client.crm.customer.dto.output.CrmCustomerListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.crm.customer.service.ICrmCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/crm/customer")
public class CrmCustomerServer implements ICrmCustomerClient {

    @Autowired
    private ICrmCustomerService customerService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated CrmCustomerCreateInputDto inputDto) {
        log.info("创建客户，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return customerService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除客户，request={}", JSONUtil.toJsonStr(request));
        return customerService.delete(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated CrmCustomerUpdateInputDto inputDto) {
        log.info("修改客户，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return customerService.update(inputDto);
    }

    @Override
    @PostMapping("detail")
    public CrmCustomerDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return customerService.detail(request);
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<CrmCustomerListOutputDto> selectPage(@RequestBody @Validated CrmCustomerQueryPageInputDto inputDto) {
        Page<CrmCustomerListOutputDto> pageResult = customerService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
