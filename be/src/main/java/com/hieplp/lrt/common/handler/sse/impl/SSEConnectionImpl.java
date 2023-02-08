package com.hieplp.lrt.common.handler.sse.impl;

import com.hieplp.lrt.common.handler.sse.ISSEConnection;
import io.vertx.core.VertxException;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;

public class SSEConnectionImpl implements ISSEConnection {

    private static final String MSG_SEPARATOR = "\n";
    private static final String PACKET_SEPARATOR = "\n\n";

    private final RoutingContext context;
    private final List<MessageConsumer> consumers = new ArrayList<>();
    private boolean rejected;
    //
    private String connectionId;
    private String refKey;

    public SSEConnectionImpl(RoutingContext context) {
        this.context = context;
    }

    @Override
    public ISSEConnection setConnectionId(String connectionId) {
        this.connectionId = connectionId;
        return this;
    }

    @Override
    public ISSEConnection setRefKey(String refKey) {
        this.refKey = refKey;
        return this;
    }

    @Override
    public ISSEConnection reject(int code) {
        return reject(code, null);
    }

    @Override
    public ISSEConnection reject(int code, String reason) {
        rejected = true;
        HttpServerResponse response = context.response();
        context.response().setStatusCode(code);
        if (reason != null) {
            context.response().setStatusMessage(reason);
        }
        context.response().end();
        return this;
    }

    @Override
    public ISSEConnection data(List<String> data) {
        return appendData(data);
    }

    @Override
    public ISSEConnection data(String data) {
        return writeData(data);
    }

    @Override
    public ISSEConnection close() {
        try {
            context.response().end(); // best effort
        } catch (VertxException | IllegalStateException e) {
            // connection has already been closed by the browser
            // do not log to avoid performance issues (ddos issue if client opening and closing a lot of connections abruptly)
        }
        if (!consumers.isEmpty()) {
            consumers.forEach(MessageConsumer::unregister);
        }
        return this;
    }

    @Override
    public ISSEConnection close(String data) {
        try {
            context.response()
//                    .setStatusCode(401)
//                    .setStatusMessage("Unauthorized")
                    .close();
        } catch (VertxException | IllegalStateException e) {
            // connection has already been closed by the browser
            // do not log to avoid performance issues (ddos issue if client opening and closing a lot of connections abruptly)
        }
        if (!consumers.isEmpty()) {
            consumers.forEach(MessageConsumer::unregister);
        }
        return this;
    }

    @Override
    public HttpServerRequest request() {
        return context.request();
    }

    @Override
    public HttpServerResponse response() {
        return context.response();
    }

    @Override
    public String getConnectionId() {
        return connectionId;
    }

    @Override
    public String getRefKey() {
        return refKey;
    }

    @Override
    public boolean rejected() {
        return rejected;
    }

    private ISSEConnection withHeader(String headerName, String headerValue, String data) {
        writeHeader(headerName, headerValue);
        writeData(data);
        return this;
    }

    private ISSEConnection withHeader(String headerName, String headerValue, List<String> data) {
        writeHeader(headerName, headerValue);
        appendData(data);
        return this;
    }

    private ISSEConnection writeHeader(String headerName, String headerValue) {
        context.response().write(headerName + ": " + headerValue + MSG_SEPARATOR);
        return this;
    }

    private ISSEConnection writeData(String data) {
        context.response().write("data: " + data + PACKET_SEPARATOR);
        return this;
    }

    private ISSEConnection appendData(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            String separator = i == data.size() - 1 ? PACKET_SEPARATOR : MSG_SEPARATOR;
            context.response().write("data: " + data.get(i) + separator);
        }
        return this;
    }
}
