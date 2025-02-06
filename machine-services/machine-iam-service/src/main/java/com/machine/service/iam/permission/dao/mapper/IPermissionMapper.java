package com.machine.service.iam.permission.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.permission.dao.mapper.entity.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

@Mapper
public interface IPermissionMapper extends BaseMapper<PermissionEntity> {

    List<PermissionEntity> listByRoleId(@Param("roleId") String roleId);

    List<PermissionEntity> selectByUserId(@Param("userId") String userId);

    List<PermissionEntity> selectByRoleIds(@Param("roleIds") Collection<String> roleIds);

}
