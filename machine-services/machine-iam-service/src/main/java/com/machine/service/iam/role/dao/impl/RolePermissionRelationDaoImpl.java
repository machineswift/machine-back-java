package com.machine.service.iam.role.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.iam.role.dao.IRolePermissionRelationDao;
import com.machine.service.iam.role.dao.mapper.IRolePermissionRelationMapper;
import com.machine.service.iam.role.dao.mapper.entity.RolePermissionRelationEntity;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;

@Repository
public class RolePermissionRelationDaoImpl implements IRolePermissionRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IRolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public void insert(List<RolePermissionRelationEntity> entityList) {
        rolePermissionRelationMapper.insert(entityList);
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_KEY);
    }

    @Override
    public int deleteByRoleId(String roleId) {
        Wrapper<RolePermissionRelationEntity> queryWrapper = new LambdaQueryWrapper<RolePermissionRelationEntity>()
                .eq(RolePermissionRelationEntity::getRoleId, roleId);
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_KEY);
        return rolePermissionRelationMapper.delete(queryWrapper);
    }

    @Override
    public List<RolePermissionRelationEntity> selectByRoleId(String roleId) {
        Wrapper<RolePermissionRelationEntity> queryWrapper = new LambdaQueryWrapper<RolePermissionRelationEntity>()
                .eq(RolePermissionRelationEntity::getRoleId, roleId);
        return rolePermissionRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<RolePermissionRelationEntity> selectByRoleIds(Collection<String> roleIds) {
        Wrapper<RolePermissionRelationEntity> queryWrapper = new LambdaQueryWrapper<RolePermissionRelationEntity>()
                .in(RolePermissionRelationEntity::getRoleId, roleIds);
        return rolePermissionRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<RolePermissionRelationEntity> selectByPermissionId(String permissionId) {
        Wrapper<RolePermissionRelationEntity> queryWrapper = new LambdaQueryWrapper<RolePermissionRelationEntity>()
                .eq(RolePermissionRelationEntity::getPermissionId, permissionId);
        return rolePermissionRelationMapper.selectList(queryWrapper);
    }
}
