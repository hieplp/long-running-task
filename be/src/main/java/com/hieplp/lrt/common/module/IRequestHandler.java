package com.hieplp.lrt.common.module;

import io.vertx.ext.web.RoutingContext;

public interface IRequestHandler {

    IRequestHandler body();

    IRequestHandler auth();

    void handle(RoutingContext context, IServiceHandler serviceHandler);
}
