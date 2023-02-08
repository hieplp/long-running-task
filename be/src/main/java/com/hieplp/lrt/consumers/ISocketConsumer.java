package com.hieplp.lrt.consumers;

import io.vertx.core.Handler;
import io.vertx.core.http.ServerWebSocket;

public interface ISocketConsumer {
    Handler<ServerWebSocket> handleSocket();

    void handleUserSocket(ServerWebSocket socket);
}
