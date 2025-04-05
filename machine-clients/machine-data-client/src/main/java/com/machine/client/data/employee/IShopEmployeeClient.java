package com.machine.client.data.employee;

import com.machine.client.data.employee.dto.input.*;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/shop_employee",
        configuration = OpenFeignDefaultConfig.class)
public interface IShopEmployeeClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataShopEmployeeCreateInputDto inputDto);

    @PostMapping("update_by_userId")
    int updateByUserId(@RequestBody @Validated DataShopEmployeeUpdateInputDto inputDto);

    @PostMapping("detail")
    ShopEmployeeDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("get_by_userId")
    ShopEmployeeDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("identity_card")
    OpenapiShopEmployeeIdentityCardOutputDto identityCard(@RequestBody @Validated IdRequest request);

    @PostMapping("health_certificate")
    OpenapiShopEmployeeHealthCertificateOutputDto healthCertificate(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_offset")
    List<ShopEmployeeListOutputDto> listByOffset(@RequestBody @Validated DataShopEmployeeQueryListOffsetInputDto inputDto);

}
