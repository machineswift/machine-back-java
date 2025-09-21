package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.sdk.common.model.response.IdCountResponse;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface IamUserRoleRelationMapper extends BaseMapper<IamUserRoleRelationEntity> {

    List<String> listUserIdByRoleIdSet(@Param("roleIdSet") Set<String> roleIdSet);

    List<IdCountResponse> countUserByRoleIdSet(@Param("roleIdSet") Set<String> roleIdSet);

}
