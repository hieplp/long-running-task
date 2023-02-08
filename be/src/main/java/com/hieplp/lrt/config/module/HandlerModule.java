package com.hieplp.lrt.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.hieplp.lrt.common.handler.redis.IRedisHandler;
import com.hieplp.lrt.common.handler.redis.RedisHandlerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HandlerModule extends AbstractModule {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        LOGGER.info("Configure handler module");
        bind(IRedisHandler.class).to(RedisHandlerImpl.class).in(Singleton.class);
    }
}
