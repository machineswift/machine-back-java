package com.machine.starter.redis.cache;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.UserAuthDetailOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.machine.starter.redis.constant.RedisConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.IAM_USER_FUNCTION_PERMISSION_LOCK;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;

@Slf4j
@Component
public class RedisCacheIamFunctionPermission {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Autowired
    private IIamUserClient userClient;

    public UserAuthDetailOutputDto functionPermission() {
        String userId = AppContext.getContext().getUserId();
        String keyCode = customerRedisCommands.get(IAM_USER_FUNCTION_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            //缓存击穿
            RLock lock = redissonClient.getLock(IAM_USER_FUNCTION_PERMISSION_LOCK);
            try {
                lock.lock();

                keyCode = customerRedisCommands.get(IAM_USER_FUNCTION_PERMISSION_KEY);
                if (StrUtil.isEmpty(keyCode)) {
                    keyCode = leaf4RedisClient.iamUserFunctionPermission();
                    customerRedisCommands.set(IAM_USER_FUNCTION_PERMISSION_KEY, keyCode, 6 * 60 * 60);
                }
            } finally {
                lock.unlock();
            }
        }

        //如果存在则直接返回数据
        String functionPermission = customerRedisCommands.get(IAM_USER_FUNCTION_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
        if (StrUtil.isNotEmpty(functionPermission)) {
            return JSONUtil.toBean(functionPermission, UserAuthDetailOutputDto.class);
        } else {
            //缓存击穿
            RLock lock = redissonClient.getLock(IAM_USER_FUNCTION_PERMISSION_LOCK + REDIS_KEY_SEPARATOR_COLON + userId);
            try {
                lock.lock();

                functionPermission = customerRedisCommands.get(IAM_USER_FUNCTION_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
                if (StrUtil.isNotEmpty(functionPermission)) {
                    return JSONUtil.toBean(functionPermission, UserAuthDetailOutputDto.class);
                }

                //查询功能权限信息
                UserAuthDetailOutputDto userDetailDto = userClient.detailAuth(new IdRequest(userId));

                //用户权限信息存储到 redis
                customerRedisCommands.set(
                        IAM_USER_FUNCTION_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId,
                        JSONUtil.toJsonStr(userDetailDto),
                        6 * 60 * 60 + 60);

                return userDetailDto;
            } finally {
                lock.unlock();
            }
        }
    }
}
