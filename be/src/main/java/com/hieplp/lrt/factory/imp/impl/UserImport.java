package com.hieplp.lrt.factory.imp.impl;

import com.hieplp.lrt.common.constant.CodeResponse;
import com.hieplp.lrt.common.constant.StaticEnum;
import com.hieplp.lrt.common.exception.DataException;
import com.hieplp.lrt.common.handler.redis.IRedisHandler;
import com.hieplp.lrt.common.handler.sse.ISSEConnection;
import com.hieplp.lrt.common.util.JsonConverter;
import com.hieplp.lrt.common.util.ValidationTool;
import com.hieplp.lrt.factory.imp.Import;
import com.hieplp.lrt.payload.request.user.ImportedUser;
import com.hieplp.lrt.repositories.sources.IUserRepo;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserImport extends Import<ImportedUser> {

    private final IUserRepo userRepo;
    private final IRedisHandler redisHandler;
    private final Map<String, Map<String, ISSEConnection>> sseConnectionMap;

    @Inject
    protected UserImport(IUserRepo userRepo,
                         IRedisHandler redisHandler,
                         Map<String, Map<String, ISSEConnection>> sseConnectionMap) {
        this.userRepo = userRepo;
        this.redisHandler = redisHandler;
        this.sseConnectionMap = sseConnectionMap;
    }

    @Override
    public Import<ImportedUser> validate() {
        LOGGER.info("Validate imported users");
        ValidationTool.checkObjectListWithAnnotation(importData);

        List<String> phoneList = new ArrayList<>();
        List<String> personalIdList = new ArrayList<>();
        List<String> duplicatedPhoneList = new ArrayList<>();
        List<String> duplicatedPersonalIdList = new ArrayList<>();

        // Find duplicated phones and personalIds in request
        for (ImportedUser user : importData) {
            if (phoneList.contains(user.getPhone()) && !duplicatedPhoneList.contains(user.getPhone())) {
                duplicatedPhoneList.add(user.getPhone());
            } else {
                phoneList.add(user.getPhone());
            }

            if (personalIdList.contains(user.getPersonalId()) && !duplicatedPersonalIdList.contains(user.getPersonalId())) {
                duplicatedPersonalIdList.add(user.getPersonalId());
            } else {
                personalIdList.add(user.getPersonalId());
            }
        }
        if (!duplicatedPhoneList.isEmpty()) {
            throw new DataException.DuplicatedPhoneException("duplicated phone list", duplicatedPhoneList);
        }
        if (!duplicatedPersonalIdList.isEmpty()) {
            throw new DataException.DuplicatedPersonalIdException("duplicated personal id list", duplicatedPersonalIdList);
        }

        // Find duplicated phones and personalIds in database
        duplicatedPhoneList = userRepo.getPhoneList(phoneList);
        if (!duplicatedPhoneList.isEmpty()) {
            throw new DataException.DuplicatedPhoneException("duplicated phone list", duplicatedPhoneList);
        }
        duplicatedPersonalIdList = userRepo.getPersonalIdList(personalIdList);
        if (!duplicatedPhoneList.isEmpty()) {
            throw new DataException.DuplicatedPersonalIdException("duplicated personal id list", duplicatedPersonalIdList);
        }

        return this;
    }

    @Override
    public Import<ImportedUser> importData() {
        LOGGER.info("Import users");

        Observable.fromCallable(() -> {
                    try {
                        userRepo.importUsers(importData, userId, user -> {
                            // Update status of user
                            user.setStatus(StaticEnum.Status.DONE.getByteStatus());
                            redisHandler.save(refKey, JsonConverter.toJson(importData), (long) (60 * 60));

                            this.sendToAllConnections(String.valueOf(user.getId()));
                        });
                        LOGGER.debug("Import users successfully");
                        this.closeAllConnections(CodeResponse.SuccessCode.SSE_SUCCESS.getCode());

                        return true;
                    } catch (Exception e) {
                        LOGGER.error("Import users failed", e);
                        this.closeAllConnections(CodeResponse.ServerErrorCode.SSE_SERVER_ERROR.getCode());
                        return false;
                    }
                })
                .onErrorComplete()
                .subscribeOn(Schedulers.io())
                .subscribe();

        return this;
    }

    private void closeAllConnections(String data) {
        if (!sseConnectionMap.containsKey(refKey)) {
            return;
        }

        // Close all connections
        sseConnectionMap.get(refKey).forEach((connectionId, connection) -> {
            LOGGER.info("Close connection {}", connectionId);
            connection
                    // Some early messages may be lost
                    .data(data)
                    .data(data)
                    .close();
        });
        sseConnectionMap.remove(refKey);

        // Delete data in redis
        redisHandler.delete(refKey);
    }

    private void sendToAllConnections(String data) {
        if (!sseConnectionMap.containsKey(refKey)) {
            return;
        }

        // Send to all connections
        sseConnectionMap.get(refKey).forEach((connectionId, connection) -> {
            LOGGER.debug("Send data {} to connection {}", data, connectionId);
            connection.data(data);
        });
    }
}