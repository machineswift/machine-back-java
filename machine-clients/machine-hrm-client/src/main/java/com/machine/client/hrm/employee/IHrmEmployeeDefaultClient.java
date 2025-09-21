package com.machine.client.hrm.employee;

import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeDetailOutputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-hrm-service/machine-hrm-service/server/hrm/employee_default", configuration = OpenFeignDefaultConfig.class)
public interface IHrmEmployeeDefaultClient {

    /**
     * 部门机构中员工的人数
     */
    @PostMapping("count_by_departmentIds")
    Integer countByDepartmentIds(@RequestBody @Validated IdSetRequest request);

    @PostMapping("detail_by_id")
    HrmEmployeeDetailOutputDto detailById(@RequestBody @Validated IdRequest request);

    @PostMapping("get_by_userId")
    HrmEmployeeDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request);

    /**
     * 查询部门负责人（参数：部门Id）
     */
    @PostMapping("list_4_charge_by_departmentId")
    List<HrmEmployeeListOutputDto> list4ChargeByDepartmentId(@RequestBody @Validated IdRequest request);

    @PostMapping("list")
    List<HrmEmployeeListOutputDto> list(@RequestBody @Validated HrmEmployeeQueryIListInputDto inputDto);

    @PostMapping("map_by_idSet")
    Map<String, HrmEmployeeDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("map_by_userIdSet")
    Map<String, HrmEmployeeDetailOutputDto> mapByUserIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("map_4_charge_by_departmentIdSet")
    Map<String, List<HrmEmployeeDetailOutputDto>> map4ChargeByDepartmentIdSet(@RequestBody @Validated IdSetRequest request);
}
