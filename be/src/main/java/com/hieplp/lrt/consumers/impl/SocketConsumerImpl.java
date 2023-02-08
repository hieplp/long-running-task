package com.hieplp.lrt.consumers.impl;

import com.hieplp.lrt.common.constant.APIConfig;
import com.hieplp.lrt.consumers.ISocketConsumer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class SocketConsumerImpl implements ISocketConsumer {

    private final Logger LOGGER = LogManager.getLogger(UserConsumerImpl.class);

    private final Vertx vertx;

    @Inject
    public SocketConsumerImpl(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public Handler<ServerWebSocket> handleSocket() {
        return socket -> {
            LOGGER.info("Socket connected: {}", socket.path());
            if (!socket.path().contains(APIConfig.Socket.USER)) {
                LOGGER.error("Socket path is not valid: {}", socket.path());
                socket.reject();
                return;
            }

            this.handleUserSocket(socket);

            socket
                    .textMessageHandler(message -> {
                        LOGGER.info("User socket received message: {}", message);
                        vertx.eventBus().publish(APIConfig.Socket.USER, message);
                    })
                    .closeHandler(event -> {
                        LOGGER.info("User socket closed: {}", socket.path());
                    });
        };
    }

    @Override
    public void handleUserSocket(ServerWebSocket socket) {
        LOGGER.info("User socket connected: {}", socket.path());

        vertx.eventBus().consumer(APIConfig.Socket.USER, message -> {
            LOGGER.info("User socket received message: {}", message.body());
            socket.writeTextMessage(message.body().toString());
        });
    }
}
