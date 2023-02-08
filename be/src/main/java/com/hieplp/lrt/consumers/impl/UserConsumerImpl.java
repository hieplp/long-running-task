package com.hieplp.lrt.consumers.impl;

import com.hieplp.lrt.Application;
import com.hieplp.lrt.common.constant.APIConfig;
import com.hieplp.lrt.common.module.IRequestHandler;
import com.hieplp.lrt.consumers.IUserConsumer;
import com.hieplp.lrt.services.IUserService;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UserConsumerImpl implements IUserConsumer {

    private final Logger LOGGER = LogManager.getLogger(UserConsumerImpl.class);

    private final IUserService userService;

    @Inject
    public UserConsumerImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void initAPIs(Router router) {
        LOGGER.info("Init APIs for user");
        //
        router.post(APIConfig.User.USER_IMPORT).handler(this::importUsers);
        router.get(APIConfig.User.USER_IMPORT + "/:refKey").handler(this::getImportUsersData);
    }

    @Override
    public void importUsers(RoutingContext context) {
        LOGGER.info("Import users");
        IRequestHandler requestHandler = Application.injector().getInstance(IRequestHandler.class);
        requestHandler
                .body()
                .handle(context, userService::importUsers);

    }

    @Override
    public void getImportUsersData(RoutingContext context) {
        LOGGER.info("Get import users data");
        IRequestHandler requestHandler = Application.injector().getInstance(IRequestHandler.class);
        requestHandler.handle(context, userService::getImportUsersData);
    }
}
