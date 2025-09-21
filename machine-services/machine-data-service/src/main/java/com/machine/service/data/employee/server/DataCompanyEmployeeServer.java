package com.machine.service.data.employee.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.employee.IDataCompanyEmployeeClient;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeUpdateInputDto;
import com.machine.client.data.employee.dto.output.DataCompanyEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.DataCompanyEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.employee.service.IDataCompanyEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("server/data/company_employee")
public class DataCompanyEmployeeServer implements IDataCompanyEmployeeClient {

    @Autowired
    private IDataCompanyEmployeeService companyEmployeeService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataCompanyEmployeeCreateInputDto inputDto) {
        log.info("新增公司员工数据，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return companyEmployeeService.create(inputDto);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DataCompanyEmployeeUpdateInputDto inputDto) {
        log.info("修改公司员工数据，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return companyEmployeeService.update(inputDto);
    }

    @Override
    @PostMapping("detail")
    public DataCompanyEmployeeDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return companyEmployeeService.detail(request);
    }

    @Override
    @PostMapping("get_by_userId")
    public DataCompanyEmployeeDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request) {
        return companyEmployeeService.getByUserId(request);
    }

    @Override
    @PostMapping("list_userId_by_condition")
    public Set<String> listUserIdByCondition(@RequestBody @Validated DataCompanyEmployeeListUserIdInputDto inputDto) {
        return companyEmployeeService.listUserIdByCondition(inputDto);
    }

    @Override
    @PostMapping("list_by_offset")
    public List<DataCompanyEmployeeListOutputDto> listByOffset(@RequestBody @Validated DataCompanyEmployeeQueryListOffsetInputDto inputDto) {
        return companyEmployeeService.listByOffset(inputDto);
    }

    @Override
    @PostMapping("map_by_userIdSet")
    public Map<String, DataCompanyEmployeeDetailOutputDto> mapByUserIdSet(@RequestBody @Validated IdSetRequest request) {
        return companyEmployeeService.mapByUserIdSet(request);
    }

}
