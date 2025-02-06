package com.machine.service.iam.user.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.iam.organization.dto.input.IamUserRoleTargetQueryListInputDto;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.user.dao.IUserRoleTargetRelationDao;
import com.machine.service.iam.user.dao.mapper.IUserRoleTargetRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleTargetRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class UserRoleTargetRelationDaoImpl implements IUserRoleTargetRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IUserRoleTargetRelationMapper userRoleTargetRelationMapper;

    @Override
    public void insert(String userId,
                       UserRoleTargetRelationEntity entity) {
        userRoleTargetRelationMapper.insert(entity);

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));
    }

    @Override
    public void batchInsert(String userId,
                            List<UserRoleTargetRelationEntity> entityList) {
        for (UserRoleTargetRelationEntity entity : entityList) {
            userRoleTargetRelationMapper.insert(entity);
        }

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));
    }

    @Override
    public int updateTargetNull(String userId,
                                String id) {
        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        return userRoleTargetRelationMapper.updateTargetNull(id);
    }

    @Override
    public int delete(UserRoleTargetRelationEntity entity) {
        String userId = entity.getUserId();

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        return userRoleTargetRelationMapper.deleteById(userId);
    }

    @Override
    public int update(UserRoleTargetRelationEntity entity) {
        String userId = entity.getUserId();

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        return userRoleTargetRelationMapper.updateById(entity);
    }

    @Override
    public void deleteByUserId(String userId) {
        Wrapper<UserRoleTargetRelationEntity> deleteWrapper = new LambdaQueryWrapper<UserRoleTargetRelationEntity>()
                .eq(UserRoleTargetRelationEntity::getUserId, userId);
        userRoleTargetRelationMapper.delete(deleteWrapper);

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));
    }

    @Override
    public void deleteByIdSet(String userId, Set<String> deleteIdSet) {
        userRoleTargetRelationMapper.deleteByIds(deleteIdSet);

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));
    }

    @Override
    public void deleteByRoleIdSet(String userId,
                                  Set<String> roleIdSet) {
        Wrapper<UserRoleTargetRelationEntity> deleteWrapper = new LambdaQueryWrapper<UserRoleTargetRelationEntity>()
                .eq(UserRoleTargetRelationEntity::getUserId, userId)
                .in(UserRoleTargetRelationEntity::getRoleId, roleIdSet);
        userRoleTargetRelationMapper.delete(deleteWrapper);

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));
    }

    @Override
    public Boolean isAssociationRoleByOrganizationIdSet(Set<String> organizationIdSet) {
        return userRoleTargetRelationMapper.isAssociationRoleByOrganizationIdSet(organizationIdSet);
    }

    @Override
    public List<String> listUserIdByOrganizationIdSet(Set<String> organizationIdSet) {
        return userRoleTargetRelationMapper.listUserIdByOrganizationIdSet(organizationIdSet);
    }

    @Override
    public List<UserRoleTargetRelationEntity> listByUserId(String userId) {
        Wrapper<UserRoleTargetRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleTargetRelationEntity>()
                .eq(UserRoleTargetRelationEntity::getUserId, userId)
                .orderByDesc(UserRoleTargetRelationEntity::getSort);
        return userRoleTargetRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserRoleTargetRelationEntity> listByUserIdSet(Set<String> userIdSet) {
        Wrapper<UserRoleTargetRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleTargetRelationEntity>()
                .in(UserRoleTargetRelationEntity::getUserId, userIdSet);
        return userRoleTargetRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserRoleTargetRelationEntity> selectByRoleId(String roleId) {
        Wrapper<UserRoleTargetRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleTargetRelationEntity>()
                .eq(UserRoleTargetRelationEntity::getRoleId, roleId)
                .orderByDesc(UserRoleTargetRelationEntity::getSort);
        return userRoleTargetRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserRoleTargetRelationEntity> listByUserAndRoleIdId(String userId,
                                                                    String roleId) {
        Wrapper<UserRoleTargetRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleTargetRelationEntity>()
                .eq(UserRoleTargetRelationEntity::getUserId, userId)
                .eq(UserRoleTargetRelationEntity::getRoleId, roleId);
        return userRoleTargetRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserRoleTargetRelationEntity> listOrganizationShopByRoleIdSet(Set<String> idSet) {
        Wrapper<UserRoleTargetRelationEntity> queryWrapper = new LambdaQueryWrapper<UserRoleTargetRelationEntity>()
                .in(UserRoleTargetRelationEntity::getRoleId, idSet);
        return userRoleTargetRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserRoleTargetRelationEntity> listByCondition(IamUserRoleTargetQueryListInputDto inputDto) {
        return userRoleTargetRelationMapper.listByCondition(inputDto);
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

}
