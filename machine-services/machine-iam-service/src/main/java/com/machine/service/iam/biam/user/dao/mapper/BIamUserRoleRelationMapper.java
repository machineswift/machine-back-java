package com.machine.service.iam.biam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.sdk.base.model.response.IdCountResponse;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface BIamUserRoleRelationMapper extends BaseMapper<BIamUserRoleRelationEntity> {

    List<String> listUserIdByRoleIdSet(@Param("roleIdSet") Set<String> roleIdSet);

    List<IdCountResponse> countUserByRoleIdSet(@Param("roleIdSet") Set<String> roleIdSet);

}
