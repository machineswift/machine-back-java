package com.machine.starter.redis.function;

import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class CustomerRedisCommands {

    private final RedisCommands<String, String> redisCommands;

    public CustomerRedisCommands(RedisCommands<String, String> redisCommands) {
        this.redisCommands = redisCommands;
    }

    public String get(String key) {
        return redisCommands.get(key);
    }

    public Long ttl(String key) {
        return redisCommands.ttl(key);
    }

    public void set(String key,
                    String value) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    redisCommands.set(key, value);
                }
            });
        } else {
            redisCommands.set(key, value);
        }
    }

    public void set(String key,
                    String value,
                    long seconds) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    redisCommands.set(key, value);
                    redisCommands.expire(key, seconds);
                }
            });
        } else {
            redisCommands.set(key, value);
            redisCommands.expire(key, seconds);
        }
    }

    public void del(String... keys) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    redisCommands.del(keys);
                }
            });
        } else {
            redisCommands.del(keys);
        }
    }

}
