package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.iam.organization.dto.input.IamUserRoleTargetQueryListInputDto;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleTargetRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface IUserRoleTargetRelationMapper extends BaseMapper<UserRoleTargetRelationEntity> {

    int updateTargetNull(@Param("id") String id);

    Boolean isAssociationRoleByOrganizationIdSet(@Param("organizationIdSet") Set<String> organizationIdSet);

    List<String> listUserIdByOrganizationIdSet(@Param("organizationIdSet")  Set<String> organizationIdSet);

    List<UserRoleTargetRelationEntity> listByCondition(@Param("inputDto") IamUserRoleTargetQueryListInputDto inputDto);

}
