package com.hieplp.lrt.consumers;

import io.vertx.ext.web.Router;

public interface ISSEConsumer {
    ISSEConsumer initUserSSE(Router router);
}
