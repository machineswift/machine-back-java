package com.machine.service.data.franchisee.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.data.franchisee.dto.input.DataFranchiseeQueryListOffsetInputDto;
import com.machine.service.data.franchisee.dao.mapper.entity.FranchiseeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FranchiseeMapper extends BaseMapper<FranchiseeEntity> {

    List<FranchiseeEntity> listByOffset(@Param("inputDto") DataFranchiseeQueryListOffsetInputDto inputDto);
}
