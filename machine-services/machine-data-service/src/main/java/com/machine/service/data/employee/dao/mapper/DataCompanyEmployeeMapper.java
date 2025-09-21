package com.machine.service.data.employee.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.service.data.employee.dao.mapper.entity.DataCompanyEmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface DataCompanyEmployeeMapper extends BaseMapper<DataCompanyEmployeeEntity> {

    Set<String> listUserIdByCondition(@Param("inputDto") DataCompanyEmployeeListUserIdInputDto inputDto);

    List<DataCompanyEmployeeEntity> listByOffset(@Param("inputDto") DataCompanyEmployeeQueryListOffsetInputDto inputDto);

}
