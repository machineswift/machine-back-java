package com.machine.service.iam.user.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.envm.iam.UserRoleBusinessTypeEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.user.dao.IUserRoleBusinessRelationDao;
import com.machine.service.iam.user.dao.mapper.IUserRoleBusinessRelationMapper;
import com.machine.service.iam.user.dao.mapper.IUserRoleRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleBusinessRelationEntity;
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
public class UserRoleBusinessRelationDaoImpl implements IUserRoleBusinessRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IUserRoleRelationMapper userRoleRelationMapper;

    @Autowired
    private IUserRoleBusinessRelationMapper userRoleBusinessRelationMapper;

    @Override
    public String insert(String userRoleRelationId,
                         String businessId,
                         UserRoleBusinessTypeEnum businessType) {

        UserRoleBusinessRelationEntity entity = new UserRoleBusinessRelationEntity();
        entity.setUserRoleRelationId(userRoleRelationId);
        entity.setBusinessId(businessId);
        entity.setBusinessType(businessType);
        userRoleBusinessRelationMapper.insert(entity);

        UserRoleRelationEntity userRoleRelationEntity = userRoleRelationMapper.selectById(userRoleRelationId);
        String userId = userRoleRelationEntity.getUserId();

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        return entity.getId();

    }

    @Override
    public void batchInsert(String userId,
                            List<UserRoleBusinessRelationEntity> entityList) {
        if (CollectionUtil.isEmpty(entityList)) {
            return;
        }


        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        for (UserRoleBusinessRelationEntity entity : entityList) {
            userRoleBusinessRelationMapper.insert(entity);
        }

    }

    @Override
    public int deleteByUk(String userRoleRelationId,
                          String businessId,
                          UserRoleBusinessTypeEnum businessType) {
        UserRoleBusinessRelationEntity entity = getByUk(userRoleRelationId, businessId, businessType);
        if (entity == null) {
            return 0;
        }

        UserRoleRelationEntity userRoleRelationEntity = userRoleRelationMapper.selectById(userRoleRelationId);
        String userId = userRoleRelationEntity.getUserId();

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        return userRoleBusinessRelationMapper.deleteById(entity.getId());
    }

    @Override
    public void deleteByUserRoleRelationIdSet(String userId,
                                              Set<String> userRoleRelationIdSet) {
        if (CollectionUtil.isEmpty(userRoleRelationIdSet)) {
            return;
        }

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        Wrapper<UserRoleBusinessRelationEntity> wrapper = new LambdaQueryWrapper<UserRoleBusinessRelationEntity>()
                .in(UserRoleBusinessRelationEntity::getUserRoleRelationId, userRoleRelationIdSet);
        userRoleBusinessRelationMapper.delete(wrapper);
    }

    @Override
    public UserRoleBusinessRelationEntity getByUk(String userRoleRelationId,
                                                  String businessId,
                                                  UserRoleBusinessTypeEnum businessType) {
        Wrapper<UserRoleBusinessRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleBusinessRelationEntity>()
                .eq(UserRoleBusinessRelationEntity::getUserRoleRelationId, userRoleRelationId)
                .eq(UserRoleBusinessRelationEntity::getBusinessId, businessId)
                .eq(UserRoleBusinessRelationEntity::getBusinessType, businessType);
        return userRoleBusinessRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<UserRoleBusinessRelationEntity> listByUserRoleRelationIdSet(Set<String> userRoleRelationIdSet) {
        if (CollectionUtil.isEmpty(userRoleRelationIdSet)) {
            return List.of();
        }

        Wrapper<UserRoleBusinessRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleBusinessRelationEntity>()
                .in(UserRoleBusinessRelationEntity::getUserRoleRelationId, userRoleRelationIdSet);
        return userRoleBusinessRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserRoleBusinessRelationEntity> listByBusinessIdSet(Set<String> businessIdSet,
                                                                    UserRoleBusinessTypeEnum businessType) {
        if (CollectionUtil.isEmpty(businessIdSet)) {
            return List.of();
        }

        Wrapper<UserRoleBusinessRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleBusinessRelationEntity>()
                .in(UserRoleBusinessRelationEntity::getBusinessId, businessIdSet)
                .eq(UserRoleBusinessRelationEntity::getBusinessType, businessType);
        return userRoleBusinessRelationMapper.selectList(queryWrapper);
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
