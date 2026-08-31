package com.machine.service.iam.biam.user.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.base.model.dto.IdDto;
import com.machine.sdk.base.model.response.IdCountResponse;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.biam.user.dao.IBIamUserRoleRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.BIamUserRoleRelationMapper;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserFunctionPermission.BIAM_USER_FUNCTION_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserFunctionPermission.BIAM_USER_FUNCTION_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserManageDataPermission.BIAM_USER_MANAGE_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserManageDataPermission.BIAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserSuperAppDataPermission.BIAM_USER_SUPER_APP_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserSuperAppDataPermission.BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class BIamUserRoleRelationDaoImpl implements IBIamUserRoleRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private BIamUserRoleRelationMapper userRoleRelationMapper;

    @Override
    public String insert(String userId,
                         String roleId) {
        BIamUserRoleRelationEntity entity = new BIamUserRoleRelationEntity();
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
                            List<BIamUserRoleRelationEntity> entityList) {
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

        Wrapper<BIamUserRoleRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserRoleRelationEntity>()
                .eq(BIamUserRoleRelationEntity::getUserId, userId);
        userRoleRelationMapper.delete(wrapper);
    }

    @Override
    public BIamUserRoleRelationEntity detail(String id) {
        return userRoleRelationMapper.selectById(id);
    }

    @Override
    public BIamUserRoleRelationEntity getByUk(String userId,
                                              String roleId) {
        Wrapper<BIamUserRoleRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserRoleRelationEntity>()
                .eq(BIamUserRoleRelationEntity::getUserId, userId)
                .eq(BIamUserRoleRelationEntity::getRoleId, roleId);
        return userRoleRelationMapper.selectOne(wrapper);
    }

    @Override
    public List<String> listUserIdByRoleIdSet(Set<String> roleIdSet) {
        return userRoleRelationMapper.listUserIdByRoleIdSet(roleIdSet);
    }

    @Override
    public List<BIamUserRoleRelationEntity> listByUserId(String userId) {
        Wrapper<BIamUserRoleRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserRoleRelationEntity>()
                .eq(BIamUserRoleRelationEntity::getUserId, userId);
        return userRoleRelationMapper.selectList(wrapper);
    }

    @Override
    public List<BIamUserRoleRelationEntity> selectByRoleId(String roleId) {
        Wrapper<BIamUserRoleRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserRoleRelationEntity>()
                .eq(BIamUserRoleRelationEntity::getRoleId, roleId);
        return userRoleRelationMapper.selectList(wrapper);
    }

    @Override
    public List<BIamUserRoleRelationEntity> listByIdSet(Set<String> idSet) {
        return userRoleRelationMapper.selectByIds(idSet);
    }

    @Override
    public List<BIamUserRoleRelationEntity> listByRoleIdSet(Set<String> roleIdSet) {
        Wrapper<BIamUserRoleRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserRoleRelationEntity>()
                .in(BIamUserRoleRelationEntity::getRoleId, roleIdSet);
        return userRoleRelationMapper.selectList(wrapper);
    }

    @Override
    public List<BIamUserRoleRelationEntity> listByUserIdSet(Set<String> userIdSet) {
        Wrapper<BIamUserRoleRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserRoleRelationEntity>()
                .in(BIamUserRoleRelationEntity::getUserId, userIdSet);
        return userRoleRelationMapper.selectList(wrapper);
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
