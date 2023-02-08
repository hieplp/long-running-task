package com.hieplp.lrt.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.hieplp.lrt.consumers.IConsumer;
import com.hieplp.lrt.consumers.ISSEConsumer;
import com.hieplp.lrt.consumers.ISocketConsumer;
import com.hieplp.lrt.consumers.IUserConsumer;
import com.hieplp.lrt.consumers.impl.ConsumerImpl;
import com.hieplp.lrt.consumers.impl.SSEConsumerImpl;
import com.hieplp.lrt.consumers.impl.SocketConsumerImpl;
import com.hieplp.lrt.consumers.impl.UserConsumerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsumerModule extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        LOGGER.info("Configure consumer module");
        bind(IConsumer.class).to(ConsumerImpl.class).in(Singleton.class);
        bind(IUserConsumer.class).to(UserConsumerImpl.class).in(Singleton.class);
        bind(ISocketConsumer.class).to(SocketConsumerImpl.class).in(Singleton.class);
        bind(ISSEConsumer.class).to(SSEConsumerImpl.class).in(Singleton.class);
    }
}
