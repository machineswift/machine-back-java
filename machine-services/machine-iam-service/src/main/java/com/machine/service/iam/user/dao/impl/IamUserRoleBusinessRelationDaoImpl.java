package com.machine.service.iam.user.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.envm.iam.role.IamUserRoleBusinessTypeEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.user.dao.IIamUserRoleBusinessRelationDao;
import com.machine.service.iam.user.dao.mapper.IamUserRoleBusinessRelationMapper;
import com.machine.service.iam.user.dao.mapper.IamUserRoleRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleBusinessRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.sdk.common.constant.CommonConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class IamUserRoleBusinessRelationDaoImpl implements IIamUserRoleBusinessRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IamUserRoleRelationMapper userRoleRelationMapper;

    @Autowired
    private IamUserRoleBusinessRelationMapper userRoleBusinessRelationMapper;

    @Override
    public String insert(String userRoleRelationId,
                         String businessId,
                         IamUserRoleBusinessTypeEnum businessType) {

        IamUserRoleBusinessRelationEntity entity = new IamUserRoleBusinessRelationEntity();
        entity.setUserRoleRelationId(userRoleRelationId);
        entity.setBusinessId(businessId);
        entity.setBusinessType(businessType);
        userRoleBusinessRelationMapper.insert(entity);

        IamUserRoleRelationEntity iamUserRoleRelationEntity = userRoleRelationMapper.selectById(userRoleRelationId);
        String userId = iamUserRoleRelationEntity.getUserId();

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
                            List<IamUserRoleBusinessRelationEntity> entityList) {
        if (CollectionUtil.isEmpty(entityList)) {
            return;
        }


        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        for (IamUserRoleBusinessRelationEntity entity : entityList) {
            userRoleBusinessRelationMapper.insert(entity);
        }

    }

    @Override
    public int deleteByUk(String userRoleRelationId,
                          String businessId,
                          IamUserRoleBusinessTypeEnum businessType) {
        IamUserRoleBusinessRelationEntity entity = getByUk(userRoleRelationId, businessId, businessType);
        if (entity == null) {
            return 0;
        }

        IamUserRoleRelationEntity iamUserRoleRelationEntity = userRoleRelationMapper.selectById(userRoleRelationId);
        String userId = iamUserRoleRelationEntity.getUserId();

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

        Wrapper<IamUserRoleBusinessRelationEntity> wrapper = new LambdaQueryWrapper<IamUserRoleBusinessRelationEntity>()
                .in(IamUserRoleBusinessRelationEntity::getUserRoleRelationId, userRoleRelationIdSet);
        userRoleBusinessRelationMapper.delete(wrapper);
    }

    @Override
    public IamUserRoleBusinessRelationEntity getByUk(String userRoleRelationId,
                                                     String businessId,
                                                     IamUserRoleBusinessTypeEnum businessType) {
        Wrapper<IamUserRoleBusinessRelationEntity> queryWrapper = new LambdaQueryWrapper<IamUserRoleBusinessRelationEntity>()
                .eq(IamUserRoleBusinessRelationEntity::getUserRoleRelationId, userRoleRelationId)
                .eq(IamUserRoleBusinessRelationEntity::getBusinessId, businessId)
                .eq(IamUserRoleBusinessRelationEntity::getBusinessType, businessType);
        return userRoleBusinessRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<IamUserRoleBusinessRelationEntity> listByUserRoleRelationIdSet(Set<String> userRoleRelationIdSet) {
        if (CollectionUtil.isEmpty(userRoleRelationIdSet)) {
            return List.of();
        }

        Wrapper<IamUserRoleBusinessRelationEntity> queryWrapper = new LambdaQueryWrapper<IamUserRoleBusinessRelationEntity>()
                .in(IamUserRoleBusinessRelationEntity::getUserRoleRelationId, userRoleRelationIdSet);
        return userRoleBusinessRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<IamUserRoleBusinessRelationEntity> listByBusinessIdSet(IamUserRoleBusinessTypeEnum businessType,
                                                                       Set<String> businessIdSet) {
        if (CollectionUtil.isEmpty(businessIdSet)) {
            return List.of();
        }

        Wrapper<IamUserRoleBusinessRelationEntity> queryWrapper = new LambdaQueryWrapper<IamUserRoleBusinessRelationEntity>()
                .in(IamUserRoleBusinessRelationEntity::getBusinessId, businessIdSet)
                .eq(IamUserRoleBusinessRelationEntity::getBusinessType, businessType);
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
