package com.machine.starter.redis.command;

import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Map;

public class CustomerRedisCommands {

    private final RedisCommands<String, String> redis;

    public CustomerRedisCommands(RedisCommands<String, String> redis) {
        this.redis = redis;
    }

    public String get(String key) {
        return redis.get(key);
    }

    public Long ttl(String key) {
        return redis.ttl(key);
    }

    /**
     * 设置值
     */
    public void set(String key,
                    String value) {
        execute(() -> redis.set(key, value));
    }

    /**
     * 设置值并过期
     */
    public void setex(String key,
                      String value,
                      long seconds) {
        execute(() ->
                redis.setex(key, seconds, value)
        );
    }

    /**
     * 删除
     */
    public void del(String... keys) {
        execute(() ->
                redis.del(keys)
        );
    }

    /**
     * Hash 设置
     */
    public void hset(String key,
                     String field,
                     String value) {
        execute(() ->
                redis.hset(key, field, value)
        );
    }

    /**
     * Hash 批量设置
     */
    public void hset(String key,
                     Map<String, String> values) {
        execute(() ->
                redis.hset(key, values)
        );
    }

    /**
     * Hash 获取
     */
    public String hget(String key,
                       String field) {
        return redis.hget(key, field);
    }

    /**
     * Hash 删除字段
     */
    public void hdel(String key, String... fields) {
        execute(() ->
                redis.hdel(key, fields)
        );
    }


    /**
     * Redis7.4 Hash Field TTL
     */
    public void hsetWithExpire(String key,
                               String field,
                               String value,
                               long seconds) {
        execute(() -> redis.eval(
                HSET_EXPIRE_SCRIPT,
                ScriptOutputType.INTEGER,
                new String[]{key},
                field,
                value,
                String.valueOf(seconds)
        ));
    }

    /**
     * 普通执行
     */
    private void execute(Runnable command) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {

                        @Override
                        public void afterCommit() {
                            command.run();
                        }
                    }
            );
        } else {
            command.run();
        }
    }

    private static final String HSET_EXPIRE_SCRIPT = """
            redis.call('HSET', KEYS[1], ARGV[1], ARGV[2])
            return redis.call(
                'HEXPIRE',
                KEYS[1],
                ARGV[3],
                'FIELDS',
                1,
                ARGV[1]
            )
            """;
}