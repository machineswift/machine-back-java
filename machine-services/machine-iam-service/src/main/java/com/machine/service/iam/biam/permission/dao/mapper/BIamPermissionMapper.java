package com.machine.service.iam.biam.permission.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.biam.permission.dao.mapper.entity.BIamPermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

@Mapper
public interface BIamPermissionMapper extends BaseMapper<BIamPermissionEntity> {

    List<String> selectIdByRoleIds(@Param("roleIds") Collection<String> roleIds);

    List<BIamPermissionEntity> listByRoleId(@Param("roleId") String roleId);

    List<BIamPermissionEntity> listByRoleIdSet(@Param("roleIds") Collection<String> roleIds);

    List<BIamPermissionEntity> selectByUserId(@Param("userId") String userId);

    List<BIamPermissionEntity> selectByRoleIds(@Param("roleIds") Collection<String> roleIds);

}
