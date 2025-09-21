package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.iam.organization.dto.input.IamUserRoleBusinessQueryListInputDto;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleBusinessRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IamUserRoleBusinessRelationMapper extends BaseMapper<IamUserRoleBusinessRelationEntity> {

    List<IamUserRoleBusinessRelationEntity> listByCondition(@Param("inputDto") IamUserRoleBusinessQueryListInputDto inputDto);

}
