package com.hieplp.lrt;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hieplp.lrt.common.exception.CommonException;
import com.hieplp.lrt.config.ModuleConfig;
import com.hieplp.lrt.consumers.IConsumer;
import io.vertx.core.AbstractVerticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application extends AbstractVerticle {

    private static Injector injector;
    private final Logger LOGGER = LogManager.getLogger(Application.class);

    public static Injector injector() {
        return injector;
    }

    @Override
    public void start() {
        try {
            LOGGER.info("Start services");

            if (context.config().isEmpty()) {
                throw new CommonException.ValidationException("Not found config file");
            }

            injector = Guice.createInjector(new ModuleConfig(context));
            IConsumer consumer = injector.getInstance(IConsumer.class);
            consumer
                    .init()
                    .cors()
                    .apis()
                    .sse()
                    .start();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Unknown error when start services: {}", e.getMessage());
        }
    }
}
