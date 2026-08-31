package com.machine.service.iam.biam.user.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.base.envm.biam.role.BIamUserRoleBusinessTypeEnum;
import com.machine.sdk.base.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.biam.user.dao.IBIamUserRoleBusinessRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.BIamUserRoleBusinessRelationMapper;
import com.machine.service.iam.biam.user.dao.mapper.BIamUserRoleRelationMapper;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleBusinessRelationEntity;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.sdk.base.constant.CommonConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserFunctionPermission.BIAM_USER_FUNCTION_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserFunctionPermission.BIAM_USER_FUNCTION_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserManageDataPermission.BIAM_USER_MANAGE_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserManageDataPermission.BIAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserSuperAppDataPermission.BIAM_USER_SUPER_APP_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserSuperAppDataPermission.BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class BIamUserRoleBusinessRelationDaoImpl implements IBIamUserRoleBusinessRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private BIamUserRoleRelationMapper userRoleRelationMapper;

    @Autowired
    private BIamUserRoleBusinessRelationMapper userRoleBusinessRelationMapper;

    @Override
    public String insert(String userRoleRelationId,
                         String businessId,
                         BIamUserRoleBusinessTypeEnum businessType) {

        BIamUserRoleBusinessRelationEntity entity = new BIamUserRoleBusinessRelationEntity();
        entity.setUserRoleRelationId(userRoleRelationId);
        entity.setBusinessId(businessId);
        entity.setBusinessType(businessType);
        userRoleBusinessRelationMapper.insert(entity);

        BIamUserRoleRelationEntity iamUserRoleRelationEntity = userRoleRelationMapper.selectById(userRoleRelationId);
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
                            List<BIamUserRoleBusinessRelationEntity> entityList) {
        if (CollectionUtil.isEmpty(entityList)) {
            return;
        }


        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        for (BIamUserRoleBusinessRelationEntity entity : entityList) {
            userRoleBusinessRelationMapper.insert(entity);
        }

    }

    @Override
    public int deleteByUk(String userRoleRelationId,
                          String businessId,
                          BIamUserRoleBusinessTypeEnum businessType) {
        BIamUserRoleBusinessRelationEntity entity = getByUk(userRoleRelationId, businessId, businessType);
        if (entity == null) {
            return 0;
        }

        BIamUserRoleRelationEntity iamUserRoleRelationEntity = userRoleRelationMapper.selectById(userRoleRelationId);
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

        Wrapper<BIamUserRoleBusinessRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserRoleBusinessRelationEntity>()
                .in(BIamUserRoleBusinessRelationEntity::getUserRoleRelationId, userRoleRelationIdSet);
        userRoleBusinessRelationMapper.delete(wrapper);
    }

    @Override
    public BIamUserRoleBusinessRelationEntity getByUk(String userRoleRelationId,
                                                      String businessId,
                                                      BIamUserRoleBusinessTypeEnum businessType) {
        Wrapper<BIamUserRoleBusinessRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserRoleBusinessRelationEntity>()
                .eq(BIamUserRoleBusinessRelationEntity::getUserRoleRelationId, userRoleRelationId)
                .eq(BIamUserRoleBusinessRelationEntity::getBusinessId, businessId)
                .eq(BIamUserRoleBusinessRelationEntity::getBusinessType, businessType);
        return userRoleBusinessRelationMapper.selectOne(wrapper);
    }

    @Override
    public List<BIamUserRoleBusinessRelationEntity> listByUserRoleRelationIdSet(Set<String> userRoleRelationIdSet) {
        if (CollectionUtil.isEmpty(userRoleRelationIdSet)) {
            return List.of();
        }

        Wrapper<BIamUserRoleBusinessRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserRoleBusinessRelationEntity>()
                .in(BIamUserRoleBusinessRelationEntity::getUserRoleRelationId, userRoleRelationIdSet);
        return userRoleBusinessRelationMapper.selectList(wrapper);
    }

    @Override
    public List<BIamUserRoleBusinessRelationEntity> listByBusinessIdSet(BIamUserRoleBusinessTypeEnum businessType,
                                                                        Set<String> businessIdSet) {
        if (CollectionUtil.isEmpty(businessIdSet)) {
            return List.of();
        }

        Wrapper<BIamUserRoleBusinessRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserRoleBusinessRelationEntity>()
                .in(BIamUserRoleBusinessRelationEntity::getBusinessId, businessIdSet)
                .eq(BIamUserRoleBusinessRelationEntity::getBusinessType, businessType);
        return userRoleBusinessRelationMapper.selectList(wrapper);
    }


    private void clearFunctionPermissionByUserId(String userId) {
        String keyCode = customerRedisCommands.get(BIAM_USER_FUNCTION_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            return;
        }
        customerRedisCommands.del(BIAM_USER_FUNCTION_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
    }

    private void clearAppDataPermissionByUserId(String userId) {
        String keyCode = customerRedisCommands.get(BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            return;
        }
        customerRedisCommands.del(BIAM_USER_SUPER_APP_DATA_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
    }

    private void clearManageDataPermissionByUserId(String userId) {
        String keyCode = customerRedisCommands.get(BIAM_USER_MANAGE_DATA_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            return;
        }
        customerRedisCommands.del(BIAM_USER_MANAGE_DATA_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
    }

}
