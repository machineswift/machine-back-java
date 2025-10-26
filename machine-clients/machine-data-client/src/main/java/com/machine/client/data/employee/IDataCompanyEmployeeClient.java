package com.machine.client.data.employee;

import com.machine.client.data.employee.dto.input.DataCompanyEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeUpdateInputDto;
import com.machine.client.data.employee.dto.output.DataCompanyEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.DataCompanyEmployeeListOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/company_employee",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataCompanyEmployeeClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataCompanyEmployeeCreateInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated DataCompanyEmployeeUpdateInputDto inputDto);

    @PostMapping("detail")
    DataCompanyEmployeeDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("get_by_userId")
    DataCompanyEmployeeDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_userId_by_condition")
    Set<String> listUserIdByCondition(@RequestBody @Validated DataCompanyEmployeeListUserIdInputDto inputDto);

    @PostMapping("list_by_offset")
    List<DataCompanyEmployeeListOutputDto> listByOffset(@RequestBody @Validated DataCompanyEmployeeQueryListOffsetInputDto inputDto);

    @PostMapping("map_by_userIdSet")
    Map<String, DataCompanyEmployeeDetailOutputDto> mapByUserIdSet(@RequestBody @Validated IdSetRequest request);

}



