package com.machine.service.data.employee.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.employee.ICompanyEmployeeClient;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeUpdateInputDto;
import com.machine.client.data.employee.dto.output.CompanyEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.CompanyEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.employee.service.ICompanyEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/data/company_employee")
public class CompanyEmployeeServer implements ICompanyEmployeeClient {

    @Autowired
    private ICompanyEmployeeService companyEmployeeService;

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
    public CompanyEmployeeDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return companyEmployeeService.detail(request);
    }

    @Override
    @PostMapping("get_by_userId")
    public CompanyEmployeeDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request) {
        return companyEmployeeService.getByUserId(request);
    }

    @Override
    @PostMapping("list_by_offset")
    public List<CompanyEmployeeListOutputDto> listByOffset(@RequestBody @Validated DataCompanyEmployeeQueryListOffsetInputDto inputDto) {
        return companyEmployeeService.listByOffset(inputDto);
    }

    @Override
    @PostMapping("map_by_userIdSet")
    public Map<String, CompanyEmployeeDetailOutputDto> mapByUserIdSet(@RequestBody @Validated IdSetRequest request) {
        return companyEmployeeService.mapByUserIdSet(request);
    }

}
