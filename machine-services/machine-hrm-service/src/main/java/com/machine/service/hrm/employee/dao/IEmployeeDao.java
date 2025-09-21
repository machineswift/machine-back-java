package com.machine.service.hrm.employee.dao;

import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.service.hrm.employee.dao.mapper.entity.EmployeeEntity;

import java.util.List;
import java.util.Set;

public interface IEmployeeDao {

    void insert(EmployeeEntity entity);

    int update(EmployeeEntity entity);

    EmployeeEntity getById(String id);

    EmployeeEntity getByUserId(String userId);

    Integer countByDepartmentIds(Set<String> departmentIdSet);

    List<EmployeeEntity> selectByIdSet(Set<String> idSet);

    List<EmployeeEntity> selectByUserIdSet(Set<String> userIdSet);
    /**
     * 查询部门负责人
     */
    List<EmployeeEntity> list4Charge(String departmentId);

    List<EmployeeEntity> list(HrmEmployeeQueryIListInputDto inputDto);

    List<EmployeeEntity> listChargeByDepartmentIdSet(Set<String> departmentIdSet);
}
