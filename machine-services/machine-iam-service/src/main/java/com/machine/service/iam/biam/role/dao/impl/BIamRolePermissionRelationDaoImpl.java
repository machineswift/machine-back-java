package com.machine.service.iam.biam.role.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.iam.biam.role.dao.IBIamRolePermissionRelationDao;
import com.machine.service.iam.biam.role.dao.mapper.BIamRolePermissionRelationMapper;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRolePermissionRelationEntity;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserFunctionPermission.BIAM_USER_FUNCTION_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserManageDataPermission.BIAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserSuperAppDataPermission.BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class BIamRolePermissionRelationDaoImpl implements IBIamRolePermissionRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private BIamRolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public void insert(List<BIamRolePermissionRelationEntity> entityList) {
        //缓存
        customerRedisCommands.del(BIAM_USER_FUNCTION_PERMISSION_KEY);
        customerRedisCommands.del(BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(BIAM_USER_MANAGE_DATA_PERMISSION_KEY);

        rolePermissionRelationMapper.insert(entityList);
    }

    @Override
    public int deleteByRoleId(String roleId) {
        //缓存
        customerRedisCommands.del(BIAM_USER_FUNCTION_PERMISSION_KEY);
        customerRedisCommands.del(BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(BIAM_USER_MANAGE_DATA_PERMISSION_KEY);

        Wrapper<BIamRolePermissionRelationEntity> wrapper = new LambdaQueryWrapper<BIamRolePermissionRelationEntity>()
                .eq(BIamRolePermissionRelationEntity::getRoleId, roleId);
        return rolePermissionRelationMapper.delete(wrapper);
    }

    @Override
    public List<BIamRolePermissionRelationEntity> selectByRoleId(String roleId) {
        Wrapper<BIamRolePermissionRelationEntity> wrapper = new LambdaQueryWrapper<BIamRolePermissionRelationEntity>()
                .eq(BIamRolePermissionRelationEntity::getRoleId, roleId);
        return rolePermissionRelationMapper.selectList(wrapper);
    }

    @Override
    public List<BIamRolePermissionRelationEntity> selectByRoleIds(Collection<String> roleIds) {
        Wrapper<BIamRolePermissionRelationEntity> wrapper = new LambdaQueryWrapper<BIamRolePermissionRelationEntity>()
                .in(BIamRolePermissionRelationEntity::getRoleId, roleIds);
        return rolePermissionRelationMapper.selectList(wrapper);
    }

    @Override
    public List<BIamRolePermissionRelationEntity> selectByPermissionId(String permissionId) {
        Wrapper<BIamRolePermissionRelationEntity> wrapper = new LambdaQueryWrapper<BIamRolePermissionRelationEntity>()
                .eq(BIamRolePermissionRelationEntity::getPermissionId, permissionId);
        return rolePermissionRelationMapper.selectList(wrapper);
    }

    @Override
    public List<BIamRolePermissionRelationEntity> selectByPermissionCode(String permissionCode) {
        return rolePermissionRelationMapper.selectByPermissionCode(permissionCode);
    }
}
