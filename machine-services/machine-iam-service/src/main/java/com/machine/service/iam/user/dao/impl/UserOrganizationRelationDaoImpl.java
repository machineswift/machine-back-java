package com.machine.service.iam.user.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.user.dao.IUserOrganizationRelationDao;
import com.machine.service.iam.user.dao.mapper.IUserOrganizationRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserOrganizationRelationEntity;
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
public class UserOrganizationRelationDaoImpl implements IUserOrganizationRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IUserOrganizationRelationMapper userOrganizationRelationMapper;

    @Override
    public int insert(String organizationId,
                      String userId,
                      UserDepartmentRelationTypeEnum type) {
        UserOrganizationRelationEntity entity = new UserOrganizationRelationEntity();
        entity.setOrganizationId(organizationId);
        entity.setUserId(userId);
        entity.setType(type);

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ORGANIZATION, new IdDto(userId));

        return userOrganizationRelationMapper.insert(entity);
    }

    @Override
    public void insertByUserId(String userId,
                               Set<String> organizationIdSet) {
        if (CollectionUtil.isEmpty(organizationIdSet)) {
            return;
        }

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ORGANIZATION, new IdDto(userId));

        for (String organizationId : organizationIdSet) {
            UserOrganizationRelationEntity entity = new UserOrganizationRelationEntity();
            entity.setOrganizationId(organizationId);
            entity.setUserId(userId);
            entity.setType(UserDepartmentRelationTypeEnum.MEMBER);
            userOrganizationRelationMapper.insert(entity);
        }
    }

    @Override
    public void deleteByUserId(String userId,
                               Set<String> organizationIdSet) {
        if (CollectionUtil.isEmpty(organizationIdSet)) {
            return;
        }

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ORGANIZATION, new IdDto(userId));

        Wrapper<UserOrganizationRelationEntity> wrapper = new LambdaQueryWrapper<UserOrganizationRelationEntity>()
                .eq(UserOrganizationRelationEntity::getUserId, userId)
                .in(UserOrganizationRelationEntity::getOrganizationId, organizationIdSet);
        userOrganizationRelationMapper.delete(wrapper);
    }

    @Override
    public int deleteLeaderByOrganizationId(String organizationId) {
        Wrapper<UserOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<UserOrganizationRelationEntity>()
                .eq(UserOrganizationRelationEntity::getOrganizationId, organizationId)
                .eq(UserOrganizationRelationEntity::getType, UserDepartmentRelationTypeEnum.LEADER);
        UserOrganizationRelationEntity entity = userOrganizationRelationMapper.selectOne(queryWrapper);
        if (entity == null) {
            return 0;
        }

        String userId = entity.getUserId();

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ORGANIZATION, new IdDto(userId));

        return userOrganizationRelationMapper.deleteById(entity.getId());
    }

    @Override
    public boolean isAssociationUserByOrganizationId(String organizationId) {
        return userOrganizationRelationMapper.listUserIdByOrganizationId(organizationId);
    }

    @Override
    public UserOrganizationRelationEntity detail(String id) {
        return userOrganizationRelationMapper.selectById(id);
    }

    @Override
    public UserOrganizationRelationEntity getLeaderByOrganizationId(String organizationId) {
        Wrapper<UserOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<UserOrganizationRelationEntity>()
                .eq(UserOrganizationRelationEntity::getOrganizationId, organizationId)
                .eq(UserOrganizationRelationEntity::getType, UserDepartmentRelationTypeEnum.LEADER);
        return userOrganizationRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<UserOrganizationRelationEntity> listByUserId(String userId) {
        Wrapper<UserOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<UserOrganizationRelationEntity>()
                .eq(UserOrganizationRelationEntity::getUserId, userId);
        return userOrganizationRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserOrganizationRelationEntity> listLeaderByOrganizationIdSet(Set<String> organizationIdIdSet) {
        Wrapper<UserOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<UserOrganizationRelationEntity>()
                .in(UserOrganizationRelationEntity::getOrganizationId, organizationIdIdSet)
                .eq(UserOrganizationRelationEntity::getType, UserDepartmentRelationTypeEnum.LEADER);
        return userOrganizationRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserOrganizationRelationEntity> listByOrganizationIdSet(Set<String> organizationIdIdSet) {
        Wrapper<UserOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<UserOrganizationRelationEntity>()
                .in(UserOrganizationRelationEntity::getOrganizationId, organizationIdIdSet);
        return userOrganizationRelationMapper.selectList(queryWrapper);
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
