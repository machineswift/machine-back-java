package com.machine.service.iam.biam.user.dao.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.user.dto.input.BIamDataUserNotBindOrganizationInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserQueryListOffsetInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserQueryPageInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamCompanyUserQueryPageInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserExportInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamShopUserQueryPageInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamSupplierUserQueryPageInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuth2SourceEnum;
import com.machine.sdk.base.model.dto.IdDto;
import com.machine.sdk.base.model.dto.IdStatusDto;
import com.machine.sdk.self.domain.iam.user.IamUserUpdatePhoneDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.biam.user.dao.IBIamUserDao;
import com.machine.service.iam.biam.user.dao.mapper.BIamUserMapper;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.machine.sdk.base.constant.CommonConstant.EMPTY_OBJECT;
import static com.machine.sdk.base.constant.CommonConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_USER_BASE;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.User.BIAM_USER_BASE_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserFunctionPermission.BIAM_USER_FUNCTION_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserFunctionPermission.BIAM_USER_FUNCTION_PERMISSION_KEY;

@Repository
public class BIamUserDaoImpl implements IBIamUserDao {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private BIamUserMapper userMapper;

    @Override
    public String insert(BIamUserEntity entity) {
        userMapper.insert(entity);
        String userId = entity.getId();

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_CREATE, new IdDto(userId));
        return userId;
    }

    @Override
    public int updateStatus(String userId,
                            StatusEnum status) {
        BIamUserEntity updateEntity = new BIamUserEntity();
        updateEntity.setId(userId);
        updateEntity.setStatus(status);

        //缓存
        customerRedisCommands.del(BIAM_USER_BASE_KEY + userId);
        clearFunctionPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_STATUS, new IdStatusDto(userId, status));
        return userMapper.updateById(updateEntity);
    }

    @Override
    public int updatePhone(String userId,
                           String phone) {
        BIamUserEntity updateEntity = new BIamUserEntity();
        updateEntity.setId(userId);
        updateEntity.setPhone(phone);

        //缓存
        customerRedisCommands.del(BIAM_USER_BASE_KEY + userId);
        clearFunctionPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_PHONE,
                new IamUserUpdatePhoneDto(userId, phone));

        return userMapper.updateById(updateEntity);
    }

    @Override
    public int updatePassword(String userId,
                              String password) {
        BIamUserEntity updateEntity = new BIamUserEntity();
        updateEntity.setId(userId);
        updateEntity.setPassword(password);

        //缓存
        customerRedisCommands.del(BIAM_USER_BASE_KEY + userId);
        clearFunctionPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_PASSWORD, new IdDto(userId));

        return userMapper.updateById(updateEntity);
    }

    @Override
    public int update(BIamUserEntity entity) {
        String userId = entity.getId();

        //缓存
        customerRedisCommands.del(BIAM_USER_BASE_KEY + userId);
        clearFunctionPermissionByUserId(userId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_UPDATE_BASE, new IdDto(userId));

        return userMapper.updateById(entity);
    }

    @Override
    public int countNotBindOrganization(BIamDataUserNotBindOrganizationInputDto inputDto) {
        return userMapper.countNotBindOrganization(inputDto);
    }

    @Override
    public BIamUserEntity getById(String userId) {
        String value = customerRedisCommands.get(BIAM_USER_BASE_KEY + userId);
        if (StrUtil.isNotEmpty(value)) {
            if (EMPTY_OBJECT.equals(value)) {
                return null;
            }
            return JSONUtil.toBean(value, BIamUserEntity.class);
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_IAM_USER_BASE + userId);
        try {
            lock.lock();

            value = customerRedisCommands.get(BIAM_USER_BASE_KEY + userId);
            if (StrUtil.isNotEmpty(value)) {
                if (EMPTY_OBJECT.equals(value)) {
                    return null;
                }
                return JSONUtil.toBean(value, BIamUserEntity.class);
            }

            BIamUserEntity entity = userMapper.selectById(userId);
            if (entity == null) {
                //缓存穿透（用户是uuid，不存在id冲突）
                customerRedisCommands.setex(BIAM_USER_BASE_KEY + userId, EMPTY_OBJECT, 24 * 60 * 60);
            } else {
                customerRedisCommands.setex(BIAM_USER_BASE_KEY + userId, JSONUtil.toJsonStr(entity), 24 * 60 * 60);
            }
            return entity;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public BIamUserEntity getByUsername(String username) {
        Wrapper<BIamUserEntity> wrapper = new LambdaQueryWrapper<BIamUserEntity>()
                .eq(BIamUserEntity::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public BIamUserEntity getByThirdPartyUuid(BIamAuth2SourceEnum source,
                                              String thirdPartyUuid) {
        return userMapper.getByThirdPartyUuid(source, thirdPartyUuid);
    }

    @Override
    public BIamUserEntity getByCode(String code) {
        Wrapper<BIamUserEntity> wrapper = new LambdaQueryWrapper<BIamUserEntity>()
                .eq(BIamUserEntity::getCode, code);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public BIamUserEntity getByPhone(String phone) {
        Wrapper<BIamUserEntity> wrapper = new LambdaQueryWrapper<BIamUserEntity>()
                .eq(BIamUserEntity::getPhone, phone);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<String> listIdByShopIdSet(Set<String> shopIdSet) {
        return userMapper.listIdByShopIdSet(shopIdSet);
    }

    @Override
    public List<String> listNotBindOrganization(BIamDataUserNotBindOrganizationInputDto inputDto) {
        return userMapper.listNotBindOrganization(inputDto);
    }

    @Override
    public List<BIamUserEntity> selectByIdSet(Set<String> idSet) {
        List<BIamUserEntity> entityList = new ArrayList<>();
        for (String id : idSet) {
            BIamUserEntity entity = getById(id);
            if (entity != null) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    @Override
    public List<BIamUserEntity> listByOffset(BIamUserQueryListOffsetInputDto inputDto) {
        return userMapper.listByOffset(inputDto);
    }

    @Override
    public Page<BIamUserEntity> selectPage(BIamUserQueryPageInputDto inputDto) {
        IPage<BIamUserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.selectPage(inputDto, page);
    }

    @Override
    public Page<BIamUserEntity> pageCompany(IamCompanyUserQueryPageInputDto inputDto) {
        IPage<BIamUserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.pageCompany(inputDto, page);
    }

    @Override
    public Page<BIamUserEntity> pageShop(IamShopUserQueryPageInputDto inputDto) {
        IPage<BIamUserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.pageShop(inputDto, page);
    }

    @Override
    public Page<BIamUserEntity> pageSupplier(IamSupplierUserQueryPageInputDto inputDto) {
        IPage<BIamUserEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userMapper.pageSupplier(inputDto, page);
    }

    @Override
    public List<BIamUserEntity> listShopUser4Export(BIamUserExportInputDto inputDto) {
        return userMapper.listShopUser4Export(inputDto);
    }

    private void clearFunctionPermissionByUserId(String userId) {
        String keyCode = customerRedisCommands.get(BIAM_USER_FUNCTION_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            return;
        }
        customerRedisCommands.del(BIAM_USER_FUNCTION_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
    }

}
