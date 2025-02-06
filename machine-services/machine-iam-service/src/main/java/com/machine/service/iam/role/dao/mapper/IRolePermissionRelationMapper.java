package com.machine.service.iam.role.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.role.dao.mapper.entity.RolePermissionRelationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRolePermissionRelationMapper extends BaseMapper<RolePermissionRelationEntity> {
}
