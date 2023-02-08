package com.hieplp.lrt.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.hieplp.lrt.common.constant.StaticEnum;
import com.hieplp.lrt.factory.imp.Import;
import com.hieplp.lrt.factory.imp.impl.UserImport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FactoryModule extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void configure() {
        LOGGER.info("Configure factory module");
        MapBinder<StaticEnum.ImportType, Import> importBinder = MapBinder.newMapBinder(binder(), StaticEnum.ImportType.class, Import.class);
        importBinder.addBinding(StaticEnum.ImportType.USER).to(UserImport.class);
        // importBinder.addBinding(StaticEnum.ImportType.ROLE).to(RoleImport.class);
    }
}
