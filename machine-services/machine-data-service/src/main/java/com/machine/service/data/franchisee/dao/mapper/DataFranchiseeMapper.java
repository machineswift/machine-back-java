package com.machine.service.data.franchisee.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.data.franchisee.dto.input.DataFranchiseeQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataFranchiseeListUserIdInputDto;
import com.machine.service.data.franchisee.dao.mapper.entity.DataFranchiseeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface DataFranchiseeMapper extends BaseMapper<DataFranchiseeEntity> {

    Set<String> listUserIdByCondition(@Param("inputDto") DataFranchiseeListUserIdInputDto inputDto);

    List<DataFranchiseeEntity> listByOffset(@Param("inputDto") DataFranchiseeQueryListOffsetInputDto inputDto);

}
