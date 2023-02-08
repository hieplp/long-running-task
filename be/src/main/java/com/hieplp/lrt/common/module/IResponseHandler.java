package com.hieplp.lrt.common.module;

import io.vertx.ext.web.RoutingContext;

public interface IResponseHandler {
    void handleInternalError(RoutingContext context, Exception e);
}
