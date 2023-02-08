package com.hieplp.lrt.common.handler.sse.impl;

import com.hieplp.lrt.common.handler.sse.ISSEConnection;
import com.hieplp.lrt.common.handler.sse.ISSEHandler;
import io.netty.buffer.Unpooled;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;

public class SSEHandlerImpl implements ISSEHandler {

    // README: DO NOT MUTATE THIS! (using EMPTY_BUFFER.appendBuffer(...) for instance)
    private static final Buffer EMPTY_BUFFER = Buffer.buffer(Unpooled.EMPTY_BUFFER);

    private final List<Handler<ISSEConnection>> connectHandlers;
    private final List<Handler<ISSEConnection>> closeHandlers;

    public SSEHandlerImpl() {
        connectHandlers = new ArrayList<>();
        closeHandlers = new ArrayList<>();
    }

    @Override
    public void handle(RoutingContext context) {
        HttpServerRequest request = context.request();
        HttpServerResponse response = context.response();
        response.setChunked(true);
        ISSEConnection connection = ISSEConnection.create(context);
        String accept = request.getHeader("Accept");
        if (accept != null && !accept.contains("text/event-stream")) {
            connection.reject(406, "Not acceptable");
            return;
        }
        response.closeHandler(aVoid -> {
            closeHandlers.forEach(closeHandler -> closeHandler.handle(connection));
            connection.close();
        });
        response.headers().add("Content-Type", "text/event-stream");
        response.headers().add("Cache-Control", "no-cache");
        response.headers().add("Connection", "keep-alive");
        connectHandlers.forEach(handler -> handler.handle(connection));
    }

    @Override
    public ISSEHandler connectHandler(Handler<ISSEConnection> handler) {
        connectHandlers.add(handler);
        return this;
    }

    @Override
    public ISSEHandler closeHandler(Handler<ISSEConnection> handler) {
        closeHandlers.add(handler);
        return this;
    }
}
