package com.hieplp.lrt.config.module;

import com.google.inject.AbstractModule;
import com.hieplp.lrt.common.module.IRequestHandler;
import com.hieplp.lrt.common.module.IResponseHandler;
import com.hieplp.lrt.common.module.impl.RequestHandlerImpl;
import com.hieplp.lrt.common.module.impl.ResponseHandlerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PayloadModule extends AbstractModule {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        logger.info("Binding payload module");
        bind(IRequestHandler.class).to(RequestHandlerImpl.class);
        bind(IResponseHandler.class).to(ResponseHandlerImpl.class);
    }
}
