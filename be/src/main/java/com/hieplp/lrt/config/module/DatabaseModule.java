package com.hieplp.lrt.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hieplp.lrt.common.config.DbConfig;
import com.hieplp.lrt.repositories.sources.IUserRepo;
import com.hieplp.lrt.repositories.sources.impl.UserRepoImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

public class DatabaseModule extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final DbConfig dbConfig;

    public DatabaseModule(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Provides
    @Singleton
    public HikariDataSource getDataSource() {
        LOGGER.info("Start SQL connection to MariaDB");

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSourceClassName(dbConfig.getDataSourceClassName());
        hikariConfig.setUsername(dbConfig.getUsername());
        hikariConfig.setPassword(dbConfig.getPassword());
        hikariConfig.addDataSourceProperty("serverName", dbConfig.getHost());
        hikariConfig.addDataSourceProperty("port", dbConfig.getPort());
        hikariConfig.addDataSourceProperty("databaseName", dbConfig.getDatabase());
        hikariConfig.addDataSourceProperty("characterEncoding", StandardCharsets.UTF_8);
        hikariConfig.setConnectionTimeout(10000);
        hikariConfig.addDataSourceProperty("serverTimezone", "Asia/Ho_Chi_Minh");
        hikariConfig.addDataSourceProperty("autoReconnect", true);
        hikariConfig.setIdleTimeout(0);
        hikariConfig.setAutoCommit(true);
        hikariConfig.setMaximumPoolSize(dbConfig.getMaxPoolSize());
        hikariConfig.setMinimumIdle(4);

        LOGGER.info("Start SQL connection successfully");
        return new HikariDataSource(hikariConfig);
    }

    @Provides
    @Singleton
    public DbConfig getDbConfig() {
        return dbConfig;
    }


    @Override
    protected void configure() {
        LOGGER.info("Configure database module");
        bind(IUserRepo.class).to(UserRepoImpl.class).in(Singleton.class);
    }
}
