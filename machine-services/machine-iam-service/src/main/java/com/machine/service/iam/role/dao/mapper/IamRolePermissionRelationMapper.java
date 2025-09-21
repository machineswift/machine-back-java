package com.machine.service.iam.role.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.role.dao.mapper.entity.IamRolePermissionRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IamRolePermissionRelationMapper extends BaseMapper<IamRolePermissionRelationEntity> {
    List<IamRolePermissionRelationEntity> selectByPermissionCode(@Param("permissionCode") String permissionCode);
}
