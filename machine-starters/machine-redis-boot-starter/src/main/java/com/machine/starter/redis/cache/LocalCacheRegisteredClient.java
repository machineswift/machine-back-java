package com.machine.starter.redis.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.machine.client.iam.auth.IIamOauth2RegisteredClientClient;
import com.machine.client.iam.auth.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocalCacheRegisteredClient {

    private static final String ALL_CACHE_KEY = "ALL";

    @Resource(name = "oauth2RegisteredClientCaffeine")
    private Cache<String, Object> caffeineCache;

    public IamOAuth2RegisteredClientDetailOutputDto getByClientId(String clientId,
                                                                  IIamOauth2RegisteredClientClient oauth2RegisteredClientClient) {
        IamOAuth2RegisteredClientDetailOutputDto registeredClient =
                (IamOAuth2RegisteredClientDetailOutputDto) caffeineCache.getIfPresent(clientId);

        if (null != registeredClient) {
            return registeredClient;
        }

        IamOAuth2RegisteredClientDetailOutputDto outputDto = oauth2RegisteredClientClient.getByClientId(clientId);
        if (null != outputDto) {
            caffeineCache.put(clientId, outputDto);
        }
        return outputDto;
    }

    @SuppressWarnings("unchecked")
    public List<String> allRegisteredClientIds(IIamOauth2RegisteredClientClient oauth2RegisteredClientClient) {
        List<String> allRegisteredClients = (List<String>) caffeineCache.getIfPresent(ALL_CACHE_KEY);

        if (null != allRegisteredClients) {
            return allRegisteredClients;
        }

        List<String> outputDtoList = oauth2RegisteredClientClient.allEnableClientId();
        if(CollectionUtil.isNotEmpty(outputDtoList)) {
            caffeineCache.put(ALL_CACHE_KEY, outputDtoList);
        }else {
            caffeineCache.put(ALL_CACHE_KEY, List.of());
        }
        return outputDtoList;
    }

}
