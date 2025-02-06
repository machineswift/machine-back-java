package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleRelationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserRoleRelationMapper extends BaseMapper<UserRoleRelationEntity> {
}
