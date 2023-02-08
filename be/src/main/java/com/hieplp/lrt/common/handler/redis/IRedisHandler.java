package com.hieplp.lrt.common.handler.redis;

import com.hieplp.lrt.common.config.RedisConfig;
import io.vertx.core.Vertx;

public interface IRedisHandler {

    static IRedisHandler createClient(Vertx vertx, RedisConfig redisConfig) {
        return new RedisHandlerImpl(vertx, redisConfig);
    }

    /**
     * Get value matched given key
     *
     * @param key key to get value
     * @return value matched given key
     */
    String get(String key);

    /**
     * Save key-value to redis
     *
     * @param key         key to save
     * @param value       value to save
     * @param expiredTime time to expired values
     */
    void save(String key, String value, Long expiredTime);

    /**
     * Delete value matched given key
     *
     * @param key key to delete
     */
    void delete(String key);
}
