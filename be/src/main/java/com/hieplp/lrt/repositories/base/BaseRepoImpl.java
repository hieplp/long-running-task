package com.hieplp.lrt.repositories.base;

import com.google.inject.Inject;
import com.hieplp.lrt.common.config.DbConfig;
import com.hieplp.lrt.common.exception.DataException;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.*;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.util.Arrays;

public class BaseRepoImpl implements IBaseRepo {

    protected final Logger LOGGER = LogManager.getLogger(this.getClass());

    private HikariDataSource dataSource;
    private DbConfig dbConfig;

    @Inject
    public BaseRepoImpl setDataSource(HikariDataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    @Inject
    public BaseRepoImpl setDbConfig(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
        return this;
    }

    @Override
    public CustomDSLContext getDSLContext() throws SQLException {
        Settings settings = new Settings();
        settings.setDebugInfoOnStackTrace(dbConfig.getDebugInfoOnStackTrace() != null && dbConfig.getDebugInfoOnStackTrace());
        settings.setExecuteLogging(dbConfig.getExecuteLogging() != null && dbConfig.getExecuteLogging());
        return new CustomDSLContext(dataSource.getConnection(), SQLDialect.MARIADB, settings);
    }

    @Override
    public int save(Record record) throws DataException.ExecuteException {
        try (CustomDSLContext context = getDSLContext()) {
            LOGGER.info("Save new record");
            return context
                    .insertInto(((TableRecord<?>) record).getTable())
                    .set(record)
                    .execute();
        } catch (Exception e) {
            LOGGER.error("Error when save new record: {}", e.getMessage());
            throw new DataException.ExecuteException("Failed to save new record: " + e.getMessage());
        }
    }

    @Override
    public void updateNotNull(Record record) {
        LOGGER.info("Update not null");
        try (CustomDSLContext context = getDSLContext()) {
            Arrays.stream(record.fields()).forEach(field -> record.changed(field, record.getValue(field) != null));
            Table<?> table = getTable(record);
            context.update(table)
                    .set(record)
                    .where(equalKey(table, record))
                    .execute();
        } catch (Exception e) {
            LOGGER.info("Error when save record: {}", e.getMessage());
            throw new DataException.ExecuteException("Unknown error when update not null record");
        }
    }

    private Table<?> getTable(Record record) {
        return ((TableRecord<?>) record).getTable();
    }

    private Condition equalKey(Table<?> table, Record record) {
        Condition condition = DSL.trueCondition();
        if (table.getPrimaryKey() == null) {
            return condition;
        }

        for (TableField field : table.getPrimaryKey().getFields()) {
            condition = condition.and(field.eq(field.getDataType().convert(record.getValue(field.getName()))));
        }
        return condition;
    }
}
