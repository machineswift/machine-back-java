package com.machine.client.crm.customer;

import com.machine.client.crm.customer.dto.input.CrmCustomerCreateInputDto;
import com.machine.client.crm.customer.dto.input.CrmCustomerQueryPageInputDto;
import com.machine.client.crm.customer.dto.input.CrmCustomerUpdateInputDto;
import com.machine.client.crm.customer.dto.output.CrmCustomerDetailOutputDto;
import com.machine.client.crm.customer.dto.output.CrmCustomerListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-crm-service/machine-crm-service/server/crm/customer",
        configuration = OpenFeignDefaultConfig.class)
public interface ICrmCustomerClient {

    @PostMapping("create")
    String create(@RequestBody @Validated CrmCustomerCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated CrmCustomerUpdateInputDto inputDto);

    @PostMapping("detail")
    CrmCustomerDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("select_page")
    PageResponse<CrmCustomerListOutputDto> selectPage(@RequestBody @Validated CrmCustomerQueryPageInputDto inputDto);
}
