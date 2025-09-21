package com.machine.service.iam.user.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.response.IdCountResponse;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.user.dao.IIamUserRoleRelationDao;
import com.machine.service.iam.user.dao.mapper.IamUserRoleRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class IamUserRoleRelationDaoImpl implements IIamUserRoleRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IamUserRoleRelationMapper userRoleRelationMapper;

    @Override
    public String insert(String userId,
                         String roleId) {
        IamUserRoleRelationEntity entity = new IamUserRoleRelationEntity();
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
    public void batchInsert(String userId,
                            List<IamUserRoleRelationEntity> entityList) {
        if (CollectionUtil.isEmpty(entityList)) {
            return;
        }

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        userRoleRelationMapper.insert(entityList);
    }

    @Override
    public void deleteByUserId(String userId) {

        //缓存
        clearFunctionPermissionByUserId(userId);
        clearAppDataPermissionByUserId(userId);
        clearManageDataPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_ROLE, new IdDto(userId));

        Wrapper<IamUserRoleRelationEntity> wrapper = new LambdaQueryWrapper<IamUserRoleRelationEntity>()
                .eq(IamUserRoleRelationEntity::getUserId, userId);
        userRoleRelationMapper.delete(wrapper);
    }

    @Override
    public IamUserRoleRelationEntity detail(String id) {
        return userRoleRelationMapper.selectById(id);
    }

    @Override
    public IamUserRoleRelationEntity getByUk(String userId,
                                             String roleId) {
        Wrapper<IamUserRoleRelationEntity> queryWrapper = new LambdaQueryWrapper<IamUserRoleRelationEntity>()
                .eq(IamUserRoleRelationEntity::getUserId, userId)
                .eq(IamUserRoleRelationEntity::getRoleId, roleId);
        return userRoleRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> listUserIdByRoleIdSet(Set<String> roleIdSet) {
        return userRoleRelationMapper.listUserIdByRoleIdSet(roleIdSet);
    }

    @Override
    public List<IamUserRoleRelationEntity> listByUserId(String userId) {
        Wrapper<IamUserRoleRelationEntity> queryWrapper = new LambdaQueryWrapper<IamUserRoleRelationEntity>()
                .eq(IamUserRoleRelationEntity::getUserId, userId);
        return userRoleRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<IamUserRoleRelationEntity> selectByRoleId(String roleId) {
        Wrapper<IamUserRoleRelationEntity> queryWrapper = new LambdaQueryWrapper<IamUserRoleRelationEntity>()
                .eq(IamUserRoleRelationEntity::getRoleId, roleId);
        return userRoleRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<IamUserRoleRelationEntity> listByIdSet(Set<String> idSet) {
        return userRoleRelationMapper.selectByIds(idSet);
    }

    @Override
    public List<IamUserRoleRelationEntity> listByRoleIdSet(Set<String> roleIdSet) {
        Wrapper<IamUserRoleRelationEntity> queryWrapper = new LambdaQueryWrapper<IamUserRoleRelationEntity>()
                .in(IamUserRoleRelationEntity::getRoleId, roleIdSet);
        return userRoleRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<IamUserRoleRelationEntity> listByUserIdSet(Set<String> userIdSet) {
        Wrapper<IamUserRoleRelationEntity> queryWrapper = new LambdaQueryWrapper<IamUserRoleRelationEntity>()
                .in(IamUserRoleRelationEntity::getUserId, userIdSet);
        return userRoleRelationMapper.selectList(queryWrapper);
    }

    @Override
    public Map<String, Integer> countUserByRoleIdSet(Set<String> roleIdSet) {
        List<IdCountResponse> responseList = userRoleRelationMapper.countUserByRoleIdSet(roleIdSet);
        if (CollectionUtil.isEmpty(responseList)) {
            return Map.of();
        }

        return responseList.stream()
                .collect(Collectors.toMap(
                        response -> response.id,
                        response -> response.count
                ));
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
