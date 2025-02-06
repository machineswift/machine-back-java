package com.machine.service.hrm.employee.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.service.hrm.employee.dao.mapper.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface IEmployeeMapper extends BaseMapper<EmployeeEntity> {

    Integer countByDepartmentIds(@Param("departmentIdSet") Set<String> departmentIdSet);

    List<EmployeeEntity> list4Charge(@Param("departmentId") String departmentId);

    List<EmployeeEntity> list(@Param("inputDto") HrmEmployeeQueryIListInputDto inputDto);

    List<EmployeeEntity> listChargeByDepartmentIdSet(@Param("departmentIdSet") Set<String> departmentIdSet);
}
