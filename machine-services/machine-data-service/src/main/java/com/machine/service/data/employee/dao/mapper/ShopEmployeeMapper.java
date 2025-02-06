package com.machine.service.data.employee.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.data.employee.dto.input.DataShopEmployeeQueryListOffsetInputDto;
import com.machine.service.data.employee.dao.mapper.entity.ShopEmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopEmployeeMapper extends BaseMapper<ShopEmployeeEntity> {

    List<ShopEmployeeEntity> listByOffset(@Param("inputDto") DataShopEmployeeQueryListOffsetInputDto inputDto);
}
