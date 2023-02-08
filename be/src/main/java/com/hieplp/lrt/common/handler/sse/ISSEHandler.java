package com.hieplp.lrt.common.handler.sse;

import com.hieplp.lrt.common.handler.sse.impl.SSEHandlerImpl;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface ISSEHandler extends Handler<RoutingContext> {

    static ISSEHandler create() {
        return new SSEHandlerImpl();
    }

    ISSEHandler connectHandler(Handler<ISSEConnection> connection);

    ISSEHandler closeHandler(Handler<ISSEConnection> connection);
}
