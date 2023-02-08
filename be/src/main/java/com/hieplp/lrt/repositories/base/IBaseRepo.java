package com.hieplp.lrt.repositories.base;

import com.hieplp.lrt.common.exception.DataException;
import org.jooq.Record;

import java.sql.SQLException;

public interface IBaseRepo {
    CustomDSLContext getDSLContext() throws SQLException;

    int save(Record record) throws DataException.ExecuteException;

    void updateNotNull(Record record);
}
