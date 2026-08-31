package com.machine.starter.redis.caffeine;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.machine.client.iam.biam.identity.IBIamOauth2RegisteredClientClient;
import com.machine.sdk.base.model.dto.biam.identity.BIamOAuth2RegisteredClientDto;
import com.machine.starter.redis.command.CustomerRedisCommands;
import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_IDENTITY_AUTH2_REGISTERED_CLIENT;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Auth2RegisteredClient.BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Auth2RegisteredClient.BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_VERSION_KEY;

@Component
public class CaffeineCacheRegisteredClient {

    private static final String ALL_CACHE_KEY = "ALL";

    private static final long CLIENT_VERSION_EXPIRE_SECONDS = 24 * 60 * 60 - 2 * 60;

    private static final long CLIENT_DATA_EXPIRE_SECONDS = 24 * 60 * 60;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Resource(name = "oauth2RegisteredClientVersionCaffeine")
    private Cache<String, Long> registeredClientVersionCache;

    @Resource(name = "registeredClientIdsCaffeine")
    private Cache<String, List<String>> registeredClientIdsCache;

    @Resource(name = "oauth2RegisteredClientDataCaffeine")
    private Cache<String, BIamOAuth2RegisteredClientDto> registeredClientDataCache;

    @Autowired
    private IBIamOauth2RegisteredClientClient oauth2RegisteredClientClient;

    public BIamOAuth2RegisteredClientDto getByClientId(String clientId) {
        if (StrUtil.isEmpty(clientId)) {
            return null;
        }

        Long version = registeredClientVersionCache.getIfPresent(clientId);

        if (null == version) {
            version = readVersionFromRedis(clientId);
            if (null == version) {
                version = loadWithLock(clientId, oauth2RegisteredClientClient);
            }
        }

        BIamOAuth2RegisteredClientDto clientDto = registeredClientDataCache.getIfPresent(clientId);
        if (null == clientDto || !Objects.equals(version, clientDto.getUpdateTime())) {
            clientDto = readDataAndFill(clientId, oauth2RegisteredClientClient);
        }
        return clientDto;
    }

    /**
     * 从 Redis 读取版本号
     */
    private Long readVersionFromRedis(String clientId) {
        String strVersion = customerRedisCommands.hget(BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_VERSION_KEY, clientId);
        return StrUtil.isEmpty(strVersion) ? null : Long.parseLong(strVersion);
    }

    /**
     * 加锁后二次确认版本号，仍为空则查库回填（防缓存击穿），返回版本号
     */
    private Long loadWithLock(String clientId,
                              IBIamOauth2RegisteredClientClient oauth2RegisteredClientClient) {
        RLock lock = redissonClient.getLock(LOCK_IAM_IDENTITY_AUTH2_REGISTERED_CLIENT + clientId);
        try {
            lock.lock();
            Long version = readVersionFromRedis(clientId);
            if (null != version) {
                return version;
            }
            return loadFromDbAndFill(clientId, oauth2RegisteredClientClient).getUpdateTime();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 读取 Redis 数据；Redis 没有则加锁查库回填（防缓存击穿），最后回填本地缓存
     */
    private BIamOAuth2RegisteredClientDto readDataAndFill(String clientId,
                                                          IBIamOauth2RegisteredClientClient oauth2RegisteredClientClient) {
        String clientJsonStr = customerRedisCommands.get(BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_DATA + clientId);
        BIamOAuth2RegisteredClientDto clientDto;
        if (StrUtil.isEmpty(clientJsonStr)) {
            RLock lock = redissonClient.getLock(LOCK_IAM_IDENTITY_AUTH2_REGISTERED_CLIENT + clientId);
            try {
                lock.lock();
                clientJsonStr = customerRedisCommands.get(BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_DATA + clientId);
                if (StrUtil.isNotEmpty(clientJsonStr)) {
                    clientDto = JSONUtil.toBean(clientJsonStr, BIamOAuth2RegisteredClientDto.class);
                } else {
                    clientDto = loadFromDbAndFill(clientId, oauth2RegisteredClientClient);
                }
            } finally {
                lock.unlock();
            }
        } else {
            clientDto = JSONUtil.toBean(clientJsonStr, BIamOAuth2RegisteredClientDto.class);
        }

        registeredClientDataCache.put(clientId, clientDto);
        registeredClientVersionCache.put(clientId, clientDto.getUpdateTime());
        return clientDto;
    }

    /**
     * 查库并回填 Redis 与本地缓存；客户端不存在抛业务异常
     */
    private BIamOAuth2RegisteredClientDto loadFromDbAndFill(String clientId,
                                                            IBIamOauth2RegisteredClientClient oauth2RegisteredClientClient) {
        BIamOAuth2RegisteredClientDto clientDto = oauth2RegisteredClientClient.getByClientId(clientId);
        if (null == clientDto) {
            throw new AuthenticationCredentialsNotFoundException("客户端验证失败，clientId=" + clientId);
        }

        Long updateTime = clientDto.getUpdateTime();

        customerRedisCommands.setex(BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_DATA + clientId,
                JSONUtil.toJsonStr(clientDto), CLIENT_DATA_EXPIRE_SECONDS);
        customerRedisCommands.hsetWithExpire(BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_VERSION_KEY,
                clientId, updateTime.toString(), CLIENT_VERSION_EXPIRE_SECONDS);

        registeredClientDataCache.put(clientId, clientDto);
        registeredClientVersionCache.put(clientId, updateTime);
        return clientDto;
    }

    public List<String> allRegisteredClientIds(IBIamOauth2RegisteredClientClient oauth2RegisteredClientClient) {
        List<String> allRegisteredClients = registeredClientIdsCache.getIfPresent(ALL_CACHE_KEY);
        if (null != allRegisteredClients) {
            return allRegisteredClients;
        }

        List<String> outputDtoList = oauth2RegisteredClientClient.allEnableClientId();
        // 空列表也短暂缓存，避免无效请求持续打库
        List<String> cached = CollectionUtil.isEmpty(outputDtoList)
                ? List.of()
                : Collections.unmodifiableList(outputDtoList);
        registeredClientIdsCache.put(ALL_CACHE_KEY, cached);
        return cached;
    }

}
