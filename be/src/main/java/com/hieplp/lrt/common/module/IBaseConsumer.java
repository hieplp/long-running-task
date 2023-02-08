package com.hieplp.lrt.common.module;

import io.vertx.ext.web.Router;

public interface IBaseConsumer {
    void initAPIs(Router router);
}
