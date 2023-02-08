package com.hieplp.lrt.common.handler.sse;

import com.hieplp.lrt.common.handler.sse.impl.SSEConnectionImpl;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public interface ISSEConnection {

    static ISSEConnection create(RoutingContext context) {
        return new SSEConnectionImpl(context);
    }

    ISSEConnection reject(int code);

    ISSEConnection reject(int code, String reason);

    ISSEConnection data(List<String> data);

    ISSEConnection data(String data);

    ISSEConnection close();

    ISSEConnection close(String data);

    boolean rejected();

    HttpServerRequest request();

    HttpServerResponse response();

    String getConnectionId();

    ISSEConnection setConnectionId(String connectionId);

    String getRefKey();

    ISSEConnection setRefKey(String refKey);
}
