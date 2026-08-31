package com.machine.service.iam.biam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.iam.biam.organization.dto.input.BIamUserRoleBusinessQueryListInputDto;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleBusinessRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BIamUserRoleBusinessRelationMapper extends BaseMapper<BIamUserRoleBusinessRelationEntity> {

    List<BIamUserRoleBusinessRelationEntity> listByCondition(@Param("inputDto") BIamUserRoleBusinessQueryListInputDto inputDto);

}
