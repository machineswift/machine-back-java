package com.machine.starter.redis;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.machine.starter.redis.function.CustomerRedisCommands;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.StaticCredentialsProvider;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.machine.sdk.common.constant.CommonConstant.SEPARATOR_COLON;

@Configuration(proxyBeanMethods = false)
public class RedisAutoConfiguration {

    @Value("${machine.redis.database:0}")
    private int database;

    @Value("${machine.redis.timeout:5000}")
    private int timeout;

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public StatefulRedisConnection<String, String> connection() {
        RedisClient client = RedisClient.create(RedisURI.builder()
                .withHost(redisProperties.getHost())
                .withPort(redisProperties.getPort()).withAuthentication(
                        new StaticCredentialsProvider("", redisProperties.getPassword().toCharArray()))
                .withDatabase(database)
                .withTimeout(Duration.ofMillis(timeout))
                .build());
        return client.connect();
    }

    /**
     * redis操作，nio性能比较友好
     */
    @Bean
    public RedisCommands<String, String> commands(StatefulRedisConnection<String, String> connection) {
        return connection.sync();
    }


    /**
     * redis操作，增加事务完成执行命令
     */
    @Bean(name = "customerRedisCommands")
    public CustomerRedisCommands customerStreamBridge(RedisCommands<String, String> redisCommands) {
        return new CustomerRedisCommands(redisCommands);
    }


    /**
     * 主要用于分布式锁（看门狗）
     */
    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + SEPARATOR_COLON + redisProperties.getPort())
                .setPassword(redisProperties.getPassword())
                .setDatabase(database)
                .setTimeout(timeout);
        config.setCodec(new JsonJacksonCodec());
        return Redisson.create(config);
    }

    /**
     * 缓存 t_oauth2_registered_client 数据
     */
    @Bean(name = "oauth2RegisteredClientCaffeine")
    public Cache<String, Object> oauth2RegisteredClientCaffeine() {
        return Caffeine.newBuilder()
                .expireAfterWrite(8, TimeUnit.MINUTES)
                .maximumSize(64)
                .build();
    }

    /**
     * 缓存 system_config 数据
     */
    @Bean(name = "systemConfigCaffeine")
    public Cache<String, Object> systemConfigCaffeine() {
        return Caffeine.newBuilder()
                .expireAfterWrite(8, TimeUnit.MINUTES)
                .maximumSize(640)
                .build();
    }
}

