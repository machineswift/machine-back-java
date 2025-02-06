package com.machine.service.data.employee.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.service.data.employee.dao.mapper.entity.CompanyEmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyEmployeeMapper extends BaseMapper<CompanyEmployeeEntity> {

    List<CompanyEmployeeEntity> listByOffset(@Param("inputDto") DataCompanyEmployeeQueryListOffsetInputDto inputDto);
}
