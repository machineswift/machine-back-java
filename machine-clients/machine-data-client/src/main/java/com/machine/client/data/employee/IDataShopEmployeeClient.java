package com.machine.client.data.employee;

import com.machine.client.data.employee.dto.input.*;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.DataShopEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.DataShopEmployeeListOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/shop_employee",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataShopEmployeeClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataShopEmployeeCreateInputDto inputDto);

    @PostMapping("update_by_userId")
    int updateByUserId(@RequestBody @Validated DataShopEmployeeUpdateInputDto inputDto);

    @PostMapping("detail")
    DataShopEmployeeDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("get_by_userId")
    DataShopEmployeeDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("identity_card")
    OpenapiShopEmployeeIdentityCardOutputDto identityCard(@RequestBody @Validated IdRequest request);

    @PostMapping("health_certificate")
    OpenapiShopEmployeeHealthCertificateOutputDto healthCertificate(@RequestBody @Validated IdRequest request);

    @PostMapping("list_userId_by_condition")
    Set<String> listUserIdByCondition(@RequestBody @Validated DataShopEmployeeListUserIdInputDto inputDto);

    @PostMapping("list_by_offset")
    List<DataShopEmployeeListOutputDto> listByOffset(@RequestBody @Validated DataShopEmployeeQueryListOffsetInputDto inputDto);

}



