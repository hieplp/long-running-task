package com.hieplp.lrt.common.module.impl;

import com.hieplp.lrt.common.constant.CodeResponse;
import com.hieplp.lrt.common.module.IResponseHandler;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseHandlerImpl implements IResponseHandler {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void handleInternalError(RoutingContext context, Exception e) {
        logger.error("Internal error: {}", e.getMessage());
        context.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(CodeResponse.ServerErrorCode.INTERNAL_SERVER.getIntCode())
                .setStatusMessage(CodeResponse.ServerErrorCode.INTERNAL_SERVER.getMessage())
                .end();
    }
}
