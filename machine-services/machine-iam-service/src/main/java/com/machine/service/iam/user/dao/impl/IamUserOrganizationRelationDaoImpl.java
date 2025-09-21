package com.machine.service.iam.user.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.organization.dao.mapper.IamOrganizationMapper;
import com.machine.service.iam.user.dao.IIamUserOrganizationRelationDao;
import com.machine.service.iam.user.dao.mapper.IamUserOrganizationRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.IamUserOrganizationRelationEntity;
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
public class IamUserOrganizationRelationDaoImpl implements IIamUserOrganizationRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IamOrganizationMapper organizationMapper;

    @Autowired
    private IamUserOrganizationRelationMapper userOrganizationRelationMapper;

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
            IamUserOrganizationRelationEntity entity = new IamUserOrganizationRelationEntity();
            entity.setOrganizationId(organizationId);
            entity.setOrganizationType(organizationMapper.selectById(organizationId).getType());
            entity.setUserId(userId);
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

        Wrapper<IamUserOrganizationRelationEntity> wrapper = new LambdaQueryWrapper<IamUserOrganizationRelationEntity>()
                .eq(IamUserOrganizationRelationEntity::getUserId, userId)
                .in(IamUserOrganizationRelationEntity::getOrganizationId, organizationIdSet);
        userOrganizationRelationMapper.delete(wrapper);
    }

    @Override
    public boolean isAssociationUserByOrganizationId(String organizationId) {
        return userOrganizationRelationMapper.listUserIdByOrganizationId(organizationId);
    }

    @Override
    public IamUserOrganizationRelationEntity detail(String id) {
        return userOrganizationRelationMapper.selectById(id);
    }

    @Override
    public List<String> listUserIdByOrganizationIdSet(Set<String> organizationIdSet) {
        return userOrganizationRelationMapper.listUserIdByOrganizationIdSet(organizationIdSet);
    }


    @Override
    public List<IamUserOrganizationRelationEntity> listByUserId(String userId) {
        Wrapper<IamUserOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<IamUserOrganizationRelationEntity>()
                .eq(IamUserOrganizationRelationEntity::getUserId, userId);
        return userOrganizationRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<IamUserOrganizationRelationEntity> listByOrganizationIdSet(Set<String> organizationIdIdSet) {
        Wrapper<IamUserOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<IamUserOrganizationRelationEntity>()
                .in(IamUserOrganizationRelationEntity::getOrganizationId, organizationIdIdSet);
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
