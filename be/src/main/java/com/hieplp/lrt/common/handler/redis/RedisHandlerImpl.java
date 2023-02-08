package com.hieplp.lrt.common.handler.redis;

import com.hieplp.lrt.common.config.RedisConfig;
import com.hieplp.lrt.common.exception.DataException;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vertx.core.Vertx;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisHandlerImpl implements IRedisHandler {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    private final Vertx vertx;
    private final RedisConfig redisConfig;
    private RedisAPI redisAPI;

    @Inject
    RedisHandlerImpl(Vertx vertx, RedisConfig redisConfig) {
        this.vertx = vertx;
        this.redisConfig = redisConfig;
        installRedis();
    }

    private void installRedis() {
        RedisOptions redisOptions = new RedisOptions()
                .setConnectionString(redisConfig.getConnectionString())
                .setPassword(redisConfig.getPassword());
        Redis redis = Redis.createClient(vertx, redisOptions)
                .connect(res -> {
                    if (res.succeeded()) {
                        LOGGER.info("Redis connected");
                    } else {
                        LOGGER.error("Redis connection failed");
                    }
                });
        redisAPI = RedisAPI.api(redis);
    }

    @Override
    public String get(String key) {
        try {
            LOGGER.info("Get value by key: {}", key);
            return Observable.create(task -> {
                        redisAPI.get(key)
                                .onSuccess(res -> {
                                    if (res == null) {
                                        LOGGER.error("Not found key: {}", key);
                                        task.onError(new DataException.NotFoundException("Not found key: " + key));
                                    } else {
                                        LOGGER.debug(res);
                                        task.onNext(res.toString());
                                        task.onComplete();
                                    }
                                })
                                .onFailure(err -> {
                                    LOGGER.error("Get value by key: {} failed: {}", key, err.getMessage());
                                    task.onError(new DataException.ExecuteException(err.getMessage()));
                                });
                    })
                    .subscribeOn(Schedulers.io())
                    .timeout(1000, TimeUnit.MILLISECONDS)
                    .blockingFirst()
                    .toString();
        } catch (DataException.NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Get value by key: {} failed: {}", key, e.getMessage());
            throw new DataException.ExecuteException(e.getMessage());
        }
    }

    @Override
    public void save(String key, String value, Long expiredTime) {
        try {

            LOGGER.debug("Save key: {}, value: {}, expiredTime: {}", key, value, expiredTime);
            Observable.create(task -> {
                        redisAPI.setex(key, String.valueOf(expiredTime), value)
                                .onSuccess(res -> {
                                    LOGGER.info("Save key: {}, value: {}, expiredTime: {} success", key, value, expiredTime);
                                    task.onComplete();
                                })
                                .onFailure(err -> {
                                    LOGGER.error("Save key: {}, value: {}, expiredTime: {} failed: {}", key, value, expiredTime, err.getMessage());
                                    task.onError(new DataException.NotFoundException(err.getMessage()));
                                });
                    })
                    .subscribeOn(Schedulers.io())
                    .timeout(1000, TimeUnit.MILLISECONDS)
                    .blockingSubscribe();
        } catch (Exception e) {
            LOGGER.error("Save key: {}, value: {}, expiredTime: {} failed: {}", key, value, expiredTime, e.getMessage());
            throw new DataException.ExecuteException(e.getMessage());
        }
    }

    @Override
    public void delete(String key) {
        try {
            LOGGER.info("Delete key: {}", key);
            Observable.create(task -> {
                        redisAPI.del(List.of(key))
                                .onSuccess(res -> {
                                    LOGGER.info("Delete key: {} success", key);
                                    task.onComplete();
                                })
                                .onFailure(err -> {
                                    LOGGER.error("Delete key: {} failed: {}", key, err.getMessage());
                                    task.onError(new DataException.NotFoundException(err.getMessage()));
                                });
                    })
                    .subscribeOn(Schedulers.io())
                    .timeout(1000, TimeUnit.MILLISECONDS)
                    .blockingSubscribe();
        } catch (Exception e) {
            LOGGER.error("Delete key: {} failed: {}", key, e.getMessage());
            throw new DataException.ExecuteException(e.getMessage());
        }
    }
}
