package com.machine.service.hrm.employee.service;

import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeDetailOutputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IHrmEmployeeService {

    Integer countByDepartmentIds(IdSetRequest request);

    HrmEmployeeDetailOutputDto detailById(IdRequest request);

    HrmEmployeeDetailOutputDto getByUserId(IdRequest request);

    /**
     * 查询部门负责人（参数：部门Id）
     */
    List<HrmEmployeeListOutputDto> list4Charge(IdRequest request);

    List<HrmEmployeeListOutputDto> list(HrmEmployeeQueryIListInputDto inputDto);

    Map<String, HrmEmployeeDetailOutputDto> mapByIdSet(IdSetRequest request);

    Map<String, HrmEmployeeDetailOutputDto> mapByUserIdSet(IdSetRequest request);

    Map<String, List<HrmEmployeeDetailOutputDto>> map4ChargeByDepartmentIdSet(IdSetRequest request);
}
