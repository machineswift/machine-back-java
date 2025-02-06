package com.machine.service.data.employee.dao;

import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.sdk.common.envm.data.CompanyEmployeeStatusEnum;
import com.machine.service.data.employee.dao.mapper.entity.CompanyEmployeeEntity;

import java.util.List;
import java.util.Set;

public interface ICompanyEmployeeDao {

    String insert(CompanyEmployeeEntity entity);

    int update(CompanyEmployeeEntity entity);

    int updateStatus(String id,
                     CompanyEmployeeStatusEnum employeeStatus);

    CompanyEmployeeEntity getById(String id);

    CompanyEmployeeEntity getByUserId(String userId);

    List<CompanyEmployeeEntity> selectByUserIdSet(Set<String> userIdSet);

    List<CompanyEmployeeEntity> listByOffset(DataCompanyEmployeeQueryListOffsetInputDto inputDto);

}
