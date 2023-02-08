package com.hieplp.lrt.consumers.impl;

import com.hieplp.lrt.config.Config;
import com.hieplp.lrt.consumers.IConsumer;
import com.hieplp.lrt.consumers.ISSEConsumer;
import com.hieplp.lrt.consumers.ISocketConsumer;
import com.hieplp.lrt.consumers.IUserConsumer;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class ConsumerImpl implements IConsumer {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final Vertx vertx;
    private final Config config;
    //
    private final IUserConsumer userConsumer;
    private final ISocketConsumer socketConsumer;
    private final ISSEConsumer sseConsumer;
    //
    private Router router;
    private HttpServer server;

    @Inject
    public ConsumerImpl(Vertx vertx,
                        Config config,
                        IUserConsumer userConsumer,
                        ISocketConsumer socketConsumer,
                        ISSEConsumer sseConsumer) {
        this.vertx = vertx;
        this.config = config;
        this.userConsumer = userConsumer;
        this.socketConsumer = socketConsumer;
        this.sseConsumer = sseConsumer;
    }

    @Override
    public IConsumer init() {
        LOGGER.info("Initializing consumer");
        router = Router.router(vertx);
        server = vertx
                .createHttpServer(new HttpServerOptions()
                        .setHost(config.getServerConfig().getHost())
                        .setPort(config.getServerConfig().getPort()))
                .webSocketHandler(socketConsumer.handleSocket());
        return this;
    }

    @Override
    public IConsumer cors() {
        LOGGER.info("Configure CORS");
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.DELETE)
                .allowedHeader("Access-Control-Request-Method")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Headers")
                .allowedHeader("token")
                .allowedHeader("Content-Type"));
        return this;
    }

    @Override
    public IConsumer apis() {
        LOGGER.info("Configure APIs");
        userConsumer.initAPIs(router);
        return this;
    }

    @Override
    public IConsumer sse() {
        LOGGER.info("Configure SSE");
        sseConsumer.initUserSSE(router);
        return this;
    }

    @Override
    public void start() {
        LOGGER.info("Start consumer");
        server
                .requestHandler(router)
                .listen(event -> {
                    if (event.succeeded()) {
                        LOGGER.info("Listen on port {}", config.getServerConfig().getPort());
                    } else {
                        LOGGER.info("Listen failed on port {} cause by {}", config.getServerConfig().getPort(), event.cause().getMessage());
                    }
                });
    }
}
