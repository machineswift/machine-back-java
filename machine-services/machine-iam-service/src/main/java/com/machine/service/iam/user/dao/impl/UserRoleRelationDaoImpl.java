package com.machine.service.iam.user.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.user.dao.IUserRoleRelationDao;
import com.machine.service.iam.user.dao.mapper.IUserRoleRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class UserRoleRelationDaoImpl implements IUserRoleRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IUserRoleRelationMapper userRoleRelationMapper;

    @Override
    public String insert(String userId,
                         String roleId) {
        UserRoleRelationEntity entity = new UserRoleRelationEntity();
        entity.setUserId(userId);
        entity.setRoleId(roleId);
        userRoleRelationMapper.insert(entity);

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        return entity.getId();
    }

    @Override
    public void insertByUserId(String userId,
                               Set<String> roleIdSet) {
        if (CollectionUtil.isEmpty(roleIdSet)) {
            return;
        }

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        for (String roleId : roleIdSet) {
            UserRoleRelationEntity entity = new UserRoleRelationEntity();
            entity.setUserId(userId);
            entity.setRoleId(roleId);
            userRoleRelationMapper.insert(entity);
        }
    }

    @Override
    public void deleteByUserId(String userId,
                               Set<String> roleIdSet) {
        if (CollectionUtil.isEmpty(roleIdSet)) {
            return;
        }

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        Wrapper<UserRoleRelationEntity> wrapper = new LambdaQueryWrapper<UserRoleRelationEntity>()
                .eq(UserRoleRelationEntity::getUserId, userId)
                .in(UserRoleRelationEntity::getRoleId, roleIdSet);
        userRoleRelationMapper.delete(wrapper);
    }

    @Override
    public UserRoleRelationEntity detail(String id) {
        return userRoleRelationMapper.selectById(id);
    }

    @Override
    public UserRoleRelationEntity getByUk(String userId,
                                          String roleId) {
        Wrapper<UserRoleRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleRelationEntity>()
                .eq(UserRoleRelationEntity::getUserId, userId)
                .eq(UserRoleRelationEntity::getRoleId, roleId);
        return userRoleRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<UserRoleRelationEntity> listByUserId(String userId) {
        Wrapper<UserRoleRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleRelationEntity>()
                .eq(UserRoleRelationEntity::getUserId, userId);
        return userRoleRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserRoleRelationEntity> selectByRoleId(String roleId) {
        Wrapper<UserRoleRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleRelationEntity>()
                .eq(UserRoleRelationEntity::getRoleId, roleId);
        return userRoleRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserRoleRelationEntity> listByIdSet(Set<String> idSet) {
        return userRoleRelationMapper.selectByIds(idSet);
    }

    @Override
    public List<UserRoleRelationEntity> listByRoleIdSet(Set<String> roleIdSet) {
        Wrapper<UserRoleRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleRelationEntity>()
                .in(UserRoleRelationEntity::getRoleId, roleIdSet);
        return userRoleRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserRoleRelationEntity> listByUserIdSet(Set<String> userIdSet) {
        Wrapper<UserRoleRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleRelationEntity>()
                .in(UserRoleRelationEntity::getUserId, userIdSet);
        return userRoleRelationMapper.selectList(queryWrapper);
    }

    private void clearFunctionPermissionByUserId(String userId) {
        String keyCode = customerRedisCommands.get(IAM_USER_FUNCTION_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            return;
        }
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
    }

    private void clearAppDataPermissionByUserId(String userId) {
        String keyCode = customerRedisCommands.get(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            return;
        }
        customerRedisCommands.del(IAM_USER_SUPER_APP_DATA_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
    }

    private void clearManageDataPermissionByUserId(String userId) {
        String keyCode = customerRedisCommands.get(IAM_USER_MANAGE_DATA_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            return;
        }
        customerRedisCommands.del(IAM_USER_MANAGE_DATA_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
    }
}
