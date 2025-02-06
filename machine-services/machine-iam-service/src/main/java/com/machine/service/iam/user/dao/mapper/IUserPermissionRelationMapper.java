package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserPermissionRelationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserPermissionRelationMapper extends BaseMapper<UserPermissionRelationEntity> {
}
