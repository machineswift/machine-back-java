package com.machine.service.iam.user.dao.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.*;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.IdStatusDto;
import com.machine.sdk.self.domain.iam.user.IamUserUpdatePhoneDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.user.dao.IUserDao;
import com.machine.service.iam.user.dao.mapper.IUserMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisConstant.REDIS_CACHE_PENETRATION_PREVENT;
import static com.machine.starter.redis.constant.RedisConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.IAM_USER_BASE_LOCK;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.User.IAM_USER_BASE_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;

@Repository
public class UserDaoImpl implements IUserDao {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IUserMapper userMapper;

    @Override
    public String insert(UserEntity insertEntity) {
        userMapper.insert(insertEntity);
        String userId = insertEntity.getId();

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_CREATE, new IdDto(userId));
        return userId;
    }

    @Override
    public int updateStatus(String userId,
                            StatusEnum status) {
        UserEntity updateEntity = new UserEntity();
        updateEntity.setId(userId);
        updateEntity.setStatus(status);

        //缓存
        customerRedisCommands.del(IAM_USER_BASE_KEY + userId);
        clearFunctionPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_STATUS, new IdStatusDto(userId, status));
        return userMapper.updateById(updateEntity);
    }

    @Override
    public int updatePhone(String userId,
                           String phone) {
        UserEntity updateEntity = new UserEntity();
        updateEntity.setId(userId);
        updateEntity.setPhone(phone);

        //缓存
        customerRedisCommands.del(IAM_USER_BASE_KEY + userId);
        clearFunctionPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_PHONE,
                new IamUserUpdatePhoneDto(userId, phone));

        return userMapper.updateById(updateEntity);
    }

    @Override
    public int updatePassword(String userId,
                              String password) {
        UserEntity updateEntity = new UserEntity();
        updateEntity.setId(userId);
        updateEntity.setPassword(password);

        //缓存
        customerRedisCommands.del(IAM_USER_BASE_KEY + userId);
        clearFunctionPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_PASSWORD, new IdDto(userId));

        return userMapper.updateById(updateEntity);
    }

    @Override
    public int update(UserEntity entity) {
        String userId = entity.getId();

        //缓存
        customerRedisCommands.del(IAM_USER_BASE_KEY + userId);
        clearFunctionPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_BASE, new IdDto(userId));

        return userMapper.updateById(entity);
    }

    @Override
    public int countNotBindOrganization(DataUserNotBindOrganizationInputDto inputDto) {
        return userMapper.countNotBindOrganization(inputDto);
    }

    @Override
    public UserEntity getById(String userId) {
        String value = customerRedisCommands.get(IAM_USER_BASE_KEY + userId);
        if (StrUtil.isNotEmpty(value)) {
            if (REDIS_CACHE_PENETRATION_PREVENT.equals(value)) {
                return null;
            }
            return JSONUtil.toBean(value, UserEntity.class);
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(IAM_USER_BASE_LOCK + userId);
        try {
            lock.lock();

            value = customerRedisCommands.get(IAM_USER_BASE_KEY + userId);
            if (StrUtil.isNotEmpty(value)) {
                if (REDIS_CACHE_PENETRATION_PREVENT.equals(value)) {
                    return null;
                }
                return JSONUtil.toBean(value, UserEntity.class);
            }

            UserEntity entity = userMapper.selectById(userId);
            if (entity == null) {
                //缓存穿透（用户是uuid，不存在id冲突）
                customerRedisCommands.set(IAM_USER_BASE_KEY + userId, REDIS_CACHE_PENETRATION_PREVENT, 24 * 60 * 60);
            } else {
                customerRedisCommands.set(IAM_USER_BASE_KEY + userId, JSONUtil.toJsonStr(entity), 24 * 60 * 60);
            }
            return entity;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public UserEntity getByUserName(String userName) {
        Wrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getUserName, userName);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public UserEntity getByCode(String code) {
        Wrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getCode, code);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public UserEntity getByPhone(String phone) {
        Wrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getPhone, phone);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> listNotBindOrganization(DataUserNotBindOrganizationInputDto inputDto) {
        return userMapper.listNotBindOrganization(inputDto);
    }

    @Override
    public List<UserEntity> selectByIdSet(Set<String> idSet) {
        List<UserEntity> entityList = new ArrayList<>();
        for (String id : idSet) {
            UserEntity entity = getById(id);
            if (entity != null) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    @Override
    public List<UserEntity> listByOffset(IamUserQueryListOffsetInputDto inputDto) {
        return userMapper.listByOffset(inputDto);
    }

    @Override
    public Page<UserEntity> page(IamUserQueryPageInputDto inputDto) {
        IPage<UserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.page(inputDto, page);
    }

    @Override
    public Page<UserEntity> pageCompany(IamCompanyUserQueryPageInputDto inputDto) {
        IPage<UserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.pageCompany(inputDto, page);
    }

    @Override
    public Page<UserEntity> pageShop(IamShopUserQueryPageInputDto inputDto) {
        IPage<UserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.pageShop(inputDto, page);
    }

    @Override
    public Page<UserEntity> pageSupplier(IamSupplierUserQueryPageInputDto inputDto) {
        IPage<UserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.pageSupplier(inputDto, page);
    }

    private void clearFunctionPermissionByUserId(String userId) {
        String keyCode = customerRedisCommands.get(IAM_USER_FUNCTION_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            return;
        }
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
    }

}
