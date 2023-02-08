package com.hieplp.lrt.consumers.impl;

import com.hieplp.lrt.common.constant.APIConfig;
import com.hieplp.lrt.common.exception.DataException;
import com.hieplp.lrt.common.handler.redis.IRedisHandler;
import com.hieplp.lrt.common.handler.sse.ISSEConnection;
import com.hieplp.lrt.common.handler.sse.ISSEHandler;
import com.hieplp.lrt.consumers.ISSEConsumer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SSEConsumerImpl implements ISSEConsumer {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final ISSEHandler userSSEHandler;
    private final IRedisHandler redisHandler;
    private final Map<String, Map<String, ISSEConnection>> sseConnectionMap;

    @Inject
    public SSEConsumerImpl(@Named("userSSEHandler") ISSEHandler userSSEHandler,
                           IRedisHandler redisHandler,
                           Map<String, Map<String, ISSEConnection>> sseConnectionMap) {
        this.userSSEHandler = userSSEHandler;
        this.redisHandler = redisHandler;
        this.sseConnectionMap = sseConnectionMap;
    }

    @Override
    public ISSEConsumer initUserSSE(Router router) {
        LOGGER.info("Initializing user SSE");

        userSSEHandler.connectHandler(connection -> {
            try {
                final HttpServerRequest request = connection.request();
                final String refKey = request.getParam("refKey");
                if (refKey == null) {
                    connection.reject(401);
                } else {
                    connection
                            .setConnectionId(UUID.randomUUID().toString())
                            .setRefKey(refKey);
                    connection.response().setStatusCode(200);
                    connection.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/event-stream");
                    connection.response().putHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
                    connection.response().write("Pong");

                    if (!sseConnectionMap.containsKey(refKey)) {
                        sseConnectionMap.put(refKey, new HashMap<>());
                    }
                    sseConnectionMap.get(refKey).put(connection.getConnectionId(), connection);

                    LOGGER.info("SSE connection: {} established to refKey: {}", connection.getConnectionId(), refKey);
                }
            } catch (DataException.NotFoundException e) {
                connection.reject(401);
            }
        });

        userSSEHandler.closeHandler(connection -> {
            final String refKey = connection.getRefKey();
            if (sseConnectionMap.containsKey(refKey)) {
                sseConnectionMap.get(refKey).remove(connection.getConnectionId());
                LOGGER.info("SSE connection: {} closed to refKey: {}", connection.getConnectionId(), refKey);
            }

            if (sseConnectionMap.get(refKey).isEmpty()) {
                sseConnectionMap.remove(refKey);
            }
        });

        router.get(APIConfig.SSE.USER).handler(userSSEHandler);

        return this;
    }
}
