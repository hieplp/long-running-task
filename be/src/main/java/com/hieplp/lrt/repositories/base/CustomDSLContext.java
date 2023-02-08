package com.hieplp.lrt.repositories.base;

import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultDSLContext;

import java.sql.Connection;
import java.sql.SQLException;


public class CustomDSLContext extends DefaultDSLContext implements AutoCloseable {

    private final transient Connection connection;

    public CustomDSLContext(Connection connection, SQLDialect dialect, Settings settings) {
        super(connection, dialect, settings);
        this.connection = connection;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
    }
}
