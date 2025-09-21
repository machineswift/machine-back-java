package com.machine.service.data.employee.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.data.employee.dto.input.DataShopEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.input.DataShopEmployeeQueryListOffsetInputDto;
import com.machine.service.data.employee.dao.mapper.entity.DataShopEmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface DataShopEmployeeMapper extends BaseMapper<DataShopEmployeeEntity> {

    Set<String> listUserIdByCondition(@Param("inputDto") DataShopEmployeeListUserIdInputDto inputDto);

    List<DataShopEmployeeEntity> listByOffset(@Param("inputDto") DataShopEmployeeQueryListOffsetInputDto inputDto);

}
