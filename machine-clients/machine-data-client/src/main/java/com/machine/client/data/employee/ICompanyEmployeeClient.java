package com.machine.client.data.employee;

import com.machine.client.data.employee.dto.input.DataCompanyEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeUpdateInputDto;
import com.machine.client.data.employee.dto.output.CompanyEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.CompanyEmployeeListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/company_employee",
        configuration = OpenFeignDefaultConfig.class)
public interface ICompanyEmployeeClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataCompanyEmployeeCreateInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated DataCompanyEmployeeUpdateInputDto inputDto);

    @PostMapping("detail")
    CompanyEmployeeDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("get_by_userId")
    CompanyEmployeeDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_offset")
    List<CompanyEmployeeListOutputDto> listByOffset(@RequestBody @Validated DataCompanyEmployeeQueryListOffsetInputDto inputDto);

    @PostMapping("map_by_userIdSet")
    Map<String, CompanyEmployeeDetailOutputDto> mapByUserIdSet(@RequestBody @Validated IdSetRequest request);
}
