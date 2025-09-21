package com.machine.service.data.employee.dao;

import com.machine.client.data.employee.dto.input.DataCompanyEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.sdk.common.envm.hrm.HrmEmployeeStatusEnum;
import com.machine.service.data.employee.dao.mapper.entity.DataCompanyEmployeeEntity;

import java.util.List;
import java.util.Set;

public interface IDataCompanyEmployeeDao {

    String insert(DataCompanyEmployeeEntity entity);

    int update(DataCompanyEmployeeEntity entity);

    int updateStatus(String id,
                     HrmEmployeeStatusEnum employeeStatus);

    DataCompanyEmployeeEntity getById(String id);

    DataCompanyEmployeeEntity getByUserId(String userId);

    Set<String> listUserIdByCondition(DataCompanyEmployeeListUserIdInputDto inputDto);

    List<DataCompanyEmployeeEntity> selectByUserIdSet(Set<String> userIdSet);

    List<DataCompanyEmployeeEntity> listByOffset(DataCompanyEmployeeQueryListOffsetInputDto inputDto);

}
