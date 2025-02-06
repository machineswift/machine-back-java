package com.machine.starter.redis.cache;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.iam.user.IIamUserRoleTargetClient;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.machine.starter.redis.constant.RedisConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.IAM_USER_APP_DATA_PERMISSION_LOCK;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Slf4j
@Component
public class RedisCacheDataPermission {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Autowired
    private IIamUserRoleTargetClient userRoleTargetClient;

    public DataPermissionDto dataPermission4SuperApp() {
        String userId = AppContext.getContext().getUserId();
        String keyCode = customerRedisCommands.get(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            //缓存击穿
            RLock lock = redissonClient.getLock(IAM_USER_APP_DATA_PERMISSION_LOCK);
            try {
                lock.lock();

                keyCode = customerRedisCommands.get(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
                if (StrUtil.isEmpty(keyCode)) {
                    keyCode = leaf4RedisClient.iamUserAppDataPermissionPermission();
                    customerRedisCommands.set(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY, keyCode, 6 * 60 * 60);
                }
            } finally {
                lock.unlock();
            }
        }

        //如果存在则直接返回数据
        String functionPermission = customerRedisCommands.get(IAM_USER_SUPER_APP_DATA_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
        if (StrUtil.isNotEmpty(functionPermission)) {
            return JSONUtil.toBean(functionPermission, DataPermissionDto.class);
        } else {
            //缓存击穿
            RLock lock = redissonClient.getLock(IAM_USER_APP_DATA_PERMISSION_LOCK + REDIS_KEY_SEPARATOR_COLON + userId);
            try {
                lock.lock();

                functionPermission = customerRedisCommands.get(IAM_USER_SUPER_APP_DATA_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
                if (StrUtil.isNotEmpty(functionPermission)) {
                    return JSONUtil.toBean(functionPermission, DataPermissionDto.class);
                }

                //查询功能权限信息
                DataPermissionDto  dataPermissionDto = userRoleTargetClient.dataPermission4SuperApp();

                //用户权限信息存储到 redis
                customerRedisCommands.set(
                        IAM_USER_SUPER_APP_DATA_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId,
                        JSONUtil.toJsonStr(dataPermissionDto),
                        6 * 60 * 60 + 60);

                return dataPermissionDto;
            } finally {
                lock.unlock();
            }
        }
    }
}
