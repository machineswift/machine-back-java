package com.machine.service.iam.permission.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.permission.dao.mapper.entity.IamPermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

@Mapper
public interface IamPermissionMapper extends BaseMapper<IamPermissionEntity> {

    List<String> selectIdByRoleIds(@Param("roleIds") Collection<String> roleIds);

    List<IamPermissionEntity> listByRoleId(@Param("roleId") String roleId);

    List<IamPermissionEntity> selectByUserId(@Param("userId") String userId);

    List<IamPermissionEntity> selectByRoleIds(@Param("roleIds") Collection<String> roleIds);

}
