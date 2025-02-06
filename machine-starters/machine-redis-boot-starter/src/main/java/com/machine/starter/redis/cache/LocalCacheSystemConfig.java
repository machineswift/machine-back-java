package com.machine.starter.redis.cache;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.machine.client.data.config.IDataOpenapiConfigClient;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class LocalCacheSystemConfig {

    @Resource(name = "systemConfigCaffeine")
    private Cache<String, Object> caffeineCache;

    @Autowired
    private IDataOpenapiConfigClient openapiConfigClient;

    @SuppressWarnings("unchecked")
    public Set<String> openApiBlankIp() {
        String cacheKey = "OPENAPI:RESOURCE_BLANK_IP";
        Set<String> blankIpSet = (Set<String>) caffeineCache.getIfPresent(cacheKey);

        if (blankIpSet != null) {
            return blankIpSet;
        }

        //保存上次执行时间
        String content = openapiConfigClient.resourceBlankIp();
        if (StrUtil.isBlank(content)) {
            blankIpSet = Set.of();
        } else {
            blankIpSet = new HashSet<>(JSONUtil.toList(content, String.class));
        }

        //设置缓存
        caffeineCache.put(cacheKey, blankIpSet);
        return blankIpSet;
    }


}
