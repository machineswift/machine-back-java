package com.machine.service.crm.customer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.customer.dto.input.CrmCustomerCreateInputDto;
import com.machine.client.crm.customer.dto.input.CrmCustomerQueryPageInputDto;
import com.machine.client.crm.customer.dto.input.CrmCustomerUpdateInputDto;
import com.machine.client.crm.customer.dto.output.CrmCustomerDetailOutputDto;
import com.machine.client.crm.customer.dto.output.CrmCustomerListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

public interface ICrmCustomerService {

    String create(CrmCustomerCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(CrmCustomerUpdateInputDto inputDto);

    CrmCustomerDetailOutputDto detail(IdRequest request);

    Page<CrmCustomerListOutputDto> selectPage(CrmCustomerQueryPageInputDto inputDto);
}
