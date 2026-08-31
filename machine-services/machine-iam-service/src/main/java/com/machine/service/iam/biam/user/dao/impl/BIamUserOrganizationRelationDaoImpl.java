package com.machine.service.iam.biam.user.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.biam.organization.dao.mapper.BIamOrganizationMapper;
import com.machine.service.iam.biam.user.dao.IBIamUserOrganizationRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.BIamUserOrganizationRelationMapper;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserOrganizationRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.sdk.base.constant.CommonBIamConstant.Organization.DATA_ORGANIZATION_VIRTUAL_NODE;
import static com.machine.sdk.base.constant.CommonConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.sdk.base.constant.CommonConstant.SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserFunctionPermission.BIAM_USER_FUNCTION_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserFunctionPermission.BIAM_USER_FUNCTION_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserManageDataPermission.BIAM_USER_MANAGE_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserManageDataPermission.BIAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserSuperAppDataPermission.BIAM_USER_SUPER_APP_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserSuperAppDataPermission.BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class BIamUserOrganizationRelationDaoImpl implements IBIamUserOrganizationRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private BIamOrganizationMapper organizationMapper;

    @Autowired
    private BIamUserOrganizationRelationMapper userOrganizationRelationMapper;

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
            BIamUserOrganizationRelationEntity entity = new BIamUserOrganizationRelationEntity();
            entity.setOrganizationId(organizationId);
            if (organizationId.endsWith(DATA_ORGANIZATION_VIRTUAL_NODE)) {
                String typeName = organizationId.substring(0, organizationId.indexOf(SEPARATOR_COLON));
                entity.setOrganizationType(BIamOrganizationTypeEnum.valueOf(typeName));
            } else {
                entity.setOrganizationType(organizationMapper.selectById(organizationId).getType());
            }
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

        Wrapper<BIamUserOrganizationRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserOrganizationRelationEntity>()
                .eq(BIamUserOrganizationRelationEntity::getUserId, userId)
                .in(BIamUserOrganizationRelationEntity::getOrganizationId, organizationIdSet);
        userOrganizationRelationMapper.delete(wrapper);
    }

    @Override
    public boolean isAssociationUserByOrganizationId(String organizationId) {
        return userOrganizationRelationMapper.listUserIdByOrganizationId(organizationId);
    }

    @Override
    public BIamUserOrganizationRelationEntity detail(String id) {
        return userOrganizationRelationMapper.selectById(id);
    }

    @Override
    public List<String> listUserIdByOrganizationIdSet(Set<String> organizationIdSet) {
        return userOrganizationRelationMapper.listUserIdByOrganizationIdSet(organizationIdSet);
    }


    @Override
    public List<BIamUserOrganizationRelationEntity> listByUserId(String userId) {
        Wrapper<BIamUserOrganizationRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserOrganizationRelationEntity>()
                .eq(BIamUserOrganizationRelationEntity::getUserId, userId);
        return userOrganizationRelationMapper.selectList(wrapper);
    }

    @Override
    public List<BIamUserOrganizationRelationEntity> listByOrganizationIdSet(Set<String> organizationIdIdSet) {
        Wrapper<BIamUserOrganizationRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserOrganizationRelationEntity>()
                .in(BIamUserOrganizationRelationEntity::getOrganizationId, organizationIdIdSet);
        return userOrganizationRelationMapper.selectList(wrapper);
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
