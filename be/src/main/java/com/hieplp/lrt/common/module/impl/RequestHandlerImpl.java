package com.hieplp.lrt.common.module.impl;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.hieplp.lrt.common.constant.CodeResponse;
import com.hieplp.lrt.common.constant.MimeType;
import com.hieplp.lrt.common.exception.CommonException;
import com.hieplp.lrt.common.module.IRequestHandler;
import com.hieplp.lrt.common.module.IServiceHandler;
import com.hieplp.lrt.common.pojo.payload.request.CommonRequest;
import com.hieplp.lrt.common.pojo.payload.request.HeaderInternalRequest;
import com.hieplp.lrt.common.pojo.payload.response.CommonResponse;
import com.hieplp.lrt.common.util.JsonConverter;
import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestHandlerImpl implements IRequestHandler {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final Vertx vertx;
    private boolean hasBody = false;
    private boolean needAuth = false;

    @Inject
    public RequestHandlerImpl(Vertx vertx) {
        this.vertx = vertx;
    }


    @Override
    public IRequestHandler body() {
        hasBody = true;
        return this;
    }

    @Override
    public IRequestHandler auth() {
        needAuth = true;
        return this;
    }

    @Override
    public void handle(RoutingContext context, IServiceHandler serviceHandler) {
        try {
            CommonRequest request = CommonRequest.builder()
                    .headers(new HeaderInternalRequest())
                    .build();

            if (hasBody) {
                context.request().bodyHandler(body -> vertx.executeBlocking(handle -> {
                    try {
                        JsonObject bodyJson = JsonConverter.toJsonObject(body.toString());
                        context.request().params().forEach(entry -> bodyJson.addProperty(entry.getKey(), entry.getValue()));

                        request.setData(bodyJson);
                        handle.complete(serviceHandler.handle(request));
                    } catch (CommonException.ForbiddenException e) {
                        LOGGER.error(e.getMessage());
                        handle.complete(new CommonResponse(CodeResponse.ClientErrorCode.FORBIDDEN));
                    } catch (Exception e) {
                        handle.fail(e);
                    }
                }, false, rs -> handleResponse(rs, context)));
            } else {
                vertx.executeBlocking(handle -> {
                    try {
                        JsonObject data = new JsonObject();
                        context.request().params().forEach(entry -> data.addProperty(entry.getKey(), entry.getValue()));
                        request.setData(data);

                        handle.complete(serviceHandler.handle(request));
                    } catch (CommonException.ForbiddenException e) {
                        LOGGER.error(e.getMessage());
                        handle.complete(new CommonResponse(CodeResponse.ClientErrorCode.FORBIDDEN));
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("Error handle cause by {}", e.getMessage());
                        handle.fail(e);
                    }
                }, false, rs -> handleResponse(rs, context));
            }
        } catch (Exception e) {
            context.response()
                    .setStatusCode(500)
                    .end();
        }
    }

    private void handleResponse(AsyncResult<?> rs, RoutingContext context) {
        if (rs.succeeded()) {
            String response = JsonConverter.toJson(rs.result());
            LOGGER.debug("Response: {}", response);
            context.response()
                    .putHeader(HttpHeaders.CONTENT_TYPE, MimeType.JSON.getContentType())
                    .setStatusCode(200)
                    .setStatusMessage("Ok")
                    .end(response);
        } else {
            LOGGER.error("Error handle cause by {}", rs.cause().getMessage());
            context.response()
                    .putHeader(HttpHeaders.CONTENT_TYPE, MimeType.JSON.getContentType())
                    .setStatusCode(500)
                    .setStatusMessage("Internal Server Error")
                    .end(JsonConverter.toJson(new CommonResponse(CodeResponse.ServerErrorCode.INTERNAL_SERVER)));
        }
    }
}
