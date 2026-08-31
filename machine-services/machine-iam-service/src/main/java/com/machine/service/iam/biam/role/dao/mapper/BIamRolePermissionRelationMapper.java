package com.machine.service.iam.biam.role.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRolePermissionRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BIamRolePermissionRelationMapper extends BaseMapper<BIamRolePermissionRelationEntity> {
    List<BIamRolePermissionRelationEntity> selectByPermissionCode(@Param("permissionCode") String permissionCode);
}
