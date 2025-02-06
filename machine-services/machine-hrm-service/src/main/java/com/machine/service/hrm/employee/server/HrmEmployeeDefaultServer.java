package com.machine.service.hrm.employee.server;

import com.machine.client.hrm.employee.IHrmEmployeeDefaultClient;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeDetailOutputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.hrm.employee.service.IHrmEmployeeService;
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
@RequestMapping("server/hrm/employee_default")
public class HrmEmployeeDefaultServer implements IHrmEmployeeDefaultClient {

    @Autowired
    private IHrmEmployeeService employeeService;

    @Override
    @PostMapping("count_by_departmentIds")
    public Integer countByDepartmentIds(@RequestBody @Validated IdSetRequest request) {
        return employeeService.countByDepartmentIds(request);
    }

    @Override
    @PostMapping("detail_by_id")
    public HrmEmployeeDetailOutputDto detailById(@RequestBody @Validated IdRequest request) {
        return employeeService.detailById(request);
    }

    @Override
    @PostMapping("get_by_userId")
    public HrmEmployeeDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request) {
        return employeeService.getByUserId(request);
    }

    @Override
    @PostMapping("list_4_charge_by_departmentId")
    public List<HrmEmployeeListOutputDto> list4ChargeByDepartmentId(@RequestBody @Validated IdRequest request) {
        return employeeService.list4Charge(request);
    }

    @Override
    @PostMapping("list")
    public List<HrmEmployeeListOutputDto> list(@RequestBody @Validated HrmEmployeeQueryIListInputDto inputDto) {
        return employeeService.list(inputDto);
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, HrmEmployeeDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        return employeeService.mapByIdSet(request);
    }

    @Override
    @PostMapping("map_by_userIdSet")
    public Map<String, HrmEmployeeDetailOutputDto> mapByUserIdSet(@RequestBody @Validated IdSetRequest request) {
        return employeeService.mapByUserIdSet(request);
    }

    @Override
    @PostMapping("map_4_charge_by_departmentIdSet")
    public Map<String, List<HrmEmployeeDetailOutputDto>> map4ChargeByDepartmentIdSet(@RequestBody @Validated IdSetRequest request) {
        return employeeService.map4ChargeByDepartmentIdSet(request);
    }

}
