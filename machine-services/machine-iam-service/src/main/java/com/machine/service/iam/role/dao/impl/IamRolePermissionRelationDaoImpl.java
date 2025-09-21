package com.machine.service.iam.role.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.iam.role.dao.IIamRolePermissionRelationDao;
import com.machine.service.iam.role.dao.mapper.IamRolePermissionRelationMapper;
import com.machine.service.iam.role.dao.mapper.entity.IamRolePermissionRelationEntity;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class IamRolePermissionRelationDaoImpl implements IIamRolePermissionRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IamRolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public void insert(List<IamRolePermissionRelationEntity> entityList) {
        //缓存
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_MANAGE_DATA_PERMISSION_KEY);

        rolePermissionRelationMapper.insert(entityList);
    }

    @Override
    public int deleteByRoleId(String roleId) {
        //缓存
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_MANAGE_DATA_PERMISSION_KEY);

        Wrapper<IamRolePermissionRelationEntity> queryWrapper = new LambdaQueryWrapper<IamRolePermissionRelationEntity>()
                .eq(IamRolePermissionRelationEntity::getRoleId, roleId);
        return rolePermissionRelationMapper.delete(queryWrapper);
    }

    @Override
    public List<IamRolePermissionRelationEntity> selectByRoleId(String roleId) {
        Wrapper<IamRolePermissionRelationEntity> queryWrapper = new LambdaQueryWrapper<IamRolePermissionRelationEntity>()
                .eq(IamRolePermissionRelationEntity::getRoleId, roleId);
        return rolePermissionRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<IamRolePermissionRelationEntity> selectByRoleIds(Collection<String> roleIds) {
        Wrapper<IamRolePermissionRelationEntity> queryWrapper = new LambdaQueryWrapper<IamRolePermissionRelationEntity>()
                .in(IamRolePermissionRelationEntity::getRoleId, roleIds);
        return rolePermissionRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<IamRolePermissionRelationEntity> selectByPermissionId(String permissionId) {
        Wrapper<IamRolePermissionRelationEntity> queryWrapper = new LambdaQueryWrapper<IamRolePermissionRelationEntity>()
                .eq(IamRolePermissionRelationEntity::getPermissionId, permissionId);
        return rolePermissionRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<IamRolePermissionRelationEntity> selectByPermissionCode(String permissionCode) {
        return rolePermissionRelationMapper.selectByPermissionCode(permissionCode);
    }
}
