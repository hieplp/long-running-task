package com.hieplp.lrt.factory.imp;

import com.hieplp.lrt.common.util.JsonConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class Import<T> {

    protected final Logger LOGGER = LogManager.getLogger(this.getClass());

    protected List<T> importData;
    protected String userId;
    protected String refKey;

    /**
     * Set data to import
     *
     * @param importData data to import
     */
    public Import<T> setImportData(List<T> importData) {
        LOGGER.info("Set import data: {}", JsonConverter.toJson(importData));
        this.importData = importData;
        return this;
    }

    /**
     * Set user who imports data
     *
     * @param userId user who imports data
     */
    public Import<T> setUserId(String userId) {
        LOGGER.info("Set user id: {}", userId);
        this.userId = userId;
        return this;
    }

    /**
     * Set reference key
     *
     * @param refKey reference key
     */
    public Import<T> setRefKey(String refKey) {
        LOGGER.info("Set ref key: {}", refKey);
        this.refKey = refKey;
        return this;
    }

    /**
     * Validate imported data
     */
    public abstract Import<T> validate();

    /**
     * Import data to database
     */
    public abstract Import<T> importData();
}
