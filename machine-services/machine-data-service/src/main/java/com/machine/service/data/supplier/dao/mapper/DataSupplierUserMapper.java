package com.machine.service.data.supplier.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.data.supplier.dto.input.DataSupplierListUserIdInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.service.data.supplier.dao.mapper.entity.DataSupplierUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface DataSupplierUserMapper extends BaseMapper<DataSupplierUserEntity> {

    Set<String> listUserIdByCondition(@Param("inputDto")  DataSupplierListUserIdInputDto inputDto);

    List<DataSupplierUserEntity> listByOffset(@Param("inputDto") DataSupplierQueryListOffsetInputDto inputDto);

}
