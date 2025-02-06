package com.machine.service.data.supplier.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.service.data.supplier.dao.mapper.entity.SupplierUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SupplierUserMapper extends BaseMapper<SupplierUserEntity> {

    List<SupplierUserEntity> listByOffset(@Param("inputDto") DataSupplierQueryListOffsetInputDto inputDto);

}
