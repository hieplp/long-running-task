package com.hieplp.lrt.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.hieplp.lrt.services.IUserService;
import com.hieplp.lrt.services.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceModule extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        LOGGER.info("Configure services module");
        bind(IUserService.class).to(UserServiceImpl.class).in(Singleton.class);
    }
}
