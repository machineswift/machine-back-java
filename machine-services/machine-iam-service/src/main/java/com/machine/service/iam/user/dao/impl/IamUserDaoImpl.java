package com.machine.service.iam.user.dao.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.userbk.dto.input.IamCompanyUserQueryPageInputDto;
import com.machine.client.iam.user.dto.input.IamUserExportInputDto;
import com.machine.client.iam.userbk.dto.input.IamShopUserQueryPageInputDto;
import com.machine.client.iam.userbk.dto.input.IamSupplierUserQueryPageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuth2SourceEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.IdStatusDto;
import com.machine.sdk.self.domain.iam.user.IamUserUpdatePhoneDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.user.dao.IIamUserDao;
import com.machine.service.iam.user.dao.mapper.IamUserMapper;
import com.machine.service.iam.user.dao.mapper.entity.IamUserEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.machine.sdk.common.constant.CommonConstant.EMPTY_OBJECT;
import static com.machine.sdk.common.constant.CommonConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_USER_BASE;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.User.IAM_USER_BASE_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;

@Repository
public class IamUserDaoImpl implements IIamUserDao {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IamUserMapper userMapper;

    @Override
    public String insert(IamUserEntity entity) {
        userMapper.insert(entity);
        String userId = entity.getId();

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_CREATE, new IdDto(userId));
        return userId;
    }

    @Override
    public int updateStatus(String userId,
                            StatusEnum status) {
        IamUserEntity updateEntity = new IamUserEntity();
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
        IamUserEntity updateEntity = new IamUserEntity();
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
        IamUserEntity updateEntity = new IamUserEntity();
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
    public int update(IamUserEntity entity) {
        String userId = entity.getId();

        //缓存
        customerRedisCommands.del(IAM_USER_BASE_KEY + userId);
        clearFunctionPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_BASE, new IdDto(userId));

        return userMapper.updateById(entity);
    }

    @Override
    public int countNotBindOrganization(IamDataUserNotBindOrganizationInputDto inputDto) {
        return userMapper.countNotBindOrganization(inputDto);
    }

    @Override
    public IamUserEntity getById(String userId) {
        String value = customerRedisCommands.get(IAM_USER_BASE_KEY + userId);
        if (StrUtil.isNotEmpty(value)) {
            if (EMPTY_OBJECT.equals(value)) {
                return null;
            }
            return JSONUtil.toBean(value, IamUserEntity.class);
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_IAM_USER_BASE + userId);
        try {
            lock.lock();

            value = customerRedisCommands.get(IAM_USER_BASE_KEY + userId);
            if (StrUtil.isNotEmpty(value)) {
                if (EMPTY_OBJECT.equals(value)) {
                    return null;
                }
                return JSONUtil.toBean(value, IamUserEntity.class);
            }

            IamUserEntity entity = userMapper.selectById(userId);
            if (entity == null) {
                //缓存穿透（用户是uuid，不存在id冲突）
                customerRedisCommands.set(IAM_USER_BASE_KEY + userId, EMPTY_OBJECT, 24 * 60 * 60);
            } else {
                customerRedisCommands.set(IAM_USER_BASE_KEY + userId, JSONUtil.toJsonStr(entity), 24 * 60 * 60);
            }
            return entity;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public IamUserEntity getByUsername(String username) {
        Wrapper<IamUserEntity> queryWrapper = new LambdaQueryWrapper<IamUserEntity>()
                .eq(IamUserEntity::getUsername, username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public IamUserEntity getByThirdPartyUuid(IamAuth2SourceEnum source,
                                             String thirdPartyUuid) {
        return userMapper.getByThirdPartyUuid(source, thirdPartyUuid);
    }

    @Override
    public IamUserEntity getByCode(String code) {
        Wrapper<IamUserEntity> queryWrapper = new LambdaQueryWrapper<IamUserEntity>()
                .eq(IamUserEntity::getCode, code);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public IamUserEntity getByPhone(String phone) {
        Wrapper<IamUserEntity> queryWrapper = new LambdaQueryWrapper<IamUserEntity>()
                .eq(IamUserEntity::getPhone, phone);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> listIdByShopIdSet(Set<String> shopIdSet) {
        return userMapper.listIdByShopIdSet(shopIdSet);
    }

    @Override
    public List<String> listNotBindOrganization(IamDataUserNotBindOrganizationInputDto inputDto) {
        return userMapper.listNotBindOrganization(inputDto);
    }

    @Override
    public List<IamUserEntity> selectByIdSet(Set<String> idSet) {
        List<IamUserEntity> entityList = new ArrayList<>();
        for (String id : idSet) {
            IamUserEntity entity = getById(id);
            if (entity != null) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    @Override
    public List<IamUserEntity> listByOffset(IamUserQueryListOffsetInputDto inputDto) {
        return userMapper.listByOffset(inputDto);
    }

    @Override
    public Page<IamUserEntity> selectPage(IamUserQueryPageInputDto inputDto) {
        IPage<IamUserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.selectPage(inputDto, page);
    }

    @Override
    public Page<IamUserEntity> pageCompany(IamCompanyUserQueryPageInputDto inputDto) {
        IPage<IamUserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.pageCompany(inputDto, page);
    }

    @Override
    public Page<IamUserEntity> pageShop(IamShopUserQueryPageInputDto inputDto) {
        IPage<IamUserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.pageShop(inputDto, page);
    }

    @Override
    public Page<IamUserEntity> pageSupplier(IamSupplierUserQueryPageInputDto inputDto) {
        IPage<IamUserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.pageSupplier(inputDto, page);
    }

    @Override
    public List<IamUserEntity> listShopUser4Export(IamUserExportInputDto inputDto) {
        return userMapper.listShopUser4Export(inputDto);
    }

    private void clearFunctionPermissionByUserId(String userId) {
        String keyCode = customerRedisCommands.get(IAM_USER_FUNCTION_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            return;
        }
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
    }

}
