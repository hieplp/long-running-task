package com.hieplp.lrt.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hieplp.lrt.common.config.RedisConfig;
import com.hieplp.lrt.common.handler.sse.ISSEConnection;
import com.hieplp.lrt.common.handler.sse.ISSEHandler;
import com.hieplp.lrt.common.util.JsonConverter;
import com.hieplp.lrt.config.module.*;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;


public class ModuleConfig extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    private final Config config;
    private final Vertx vertx;

    public ModuleConfig(Context context) {
        this.config = JsonConverter.fromJson(context.config().encode(), Config.class);
        this.vertx = Vertx.vertx(new VertxOptions()
                .setWorkerPoolSize(config.getWorkerPoolSize())
                .setMaxWorkerExecuteTime(config.getWorkerMaxExecuteTime()));
    }

    @Provides
    @Singleton
    public Config getConfig() {
        return this.config;
    }

    @Provides
    @Singleton
    public RedisConfig getRedisConfig() {
        return this.config.getRedisConfig();
    }

    @Provides
    @Singleton
    public Vertx getVertx() {
        return this.vertx;
    }

    @Provides
    @Singleton
    public Map<String, Map<String, ISSEConnection>> getSSEConnectionMap() {
        return new HashMap<>();
    }

    @Provides
    @Singleton
    @Named("userSSEHandler")
    public ISSEHandler getUserSSEHandler() {
        return ISSEHandler.create();
    }

    @Override
    protected void configure() {
        LOGGER.info("Configure module");
        install(new ConsumerModule());
        install(new ServiceModule());
        install(new FactoryModule());
        install(new DatabaseModule(config.getDbConfig()));
        install(new PayloadModule());
        install(new HandlerModule());
    }
}
