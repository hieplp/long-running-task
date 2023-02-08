package com.hieplp.lrt.consumers;

import com.hieplp.lrt.common.module.IBaseConsumer;
import io.vertx.ext.web.RoutingContext;

public interface IUserConsumer extends IBaseConsumer {
    void importUsers(RoutingContext context);

    void getImportUsersData(RoutingContext context);
}
