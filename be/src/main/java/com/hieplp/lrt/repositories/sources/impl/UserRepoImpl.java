package com.hieplp.lrt.repositories.sources.impl;

import com.hieplp.lrt.common.exception.DataException;
import com.hieplp.lrt.common.handler.UserImportHandler;
import com.hieplp.lrt.common.util.JsonConverter;
import com.hieplp.lrt.payload.request.user.ImportedUser;
import com.hieplp.lrt.repositories.base.BaseRepoImpl;
import com.hieplp.lrt.repositories.base.CustomDSLContext;
import com.hieplp.lrt.repositories.generate.Tables;
import com.hieplp.lrt.repositories.generate.tables.records.UserRecord;
import com.hieplp.lrt.repositories.sources.IUserRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserRepoImpl extends BaseRepoImpl implements IUserRepo {
    @Override
    public List<String> getPhoneList(List<String> phoneList) {
        try (CustomDSLContext context = getDSLContext()) {
            LOGGER.info("Get phone list: {}", JsonConverter.toJson(phoneList));
            return context.select(Tables.USER.PHONE)
                    .from(Tables.USER)
                    .where(Tables.USER.PHONE.in(phoneList))
                    .fetchInto(String.class);
        } catch (Exception e) {
            LOGGER.error("Error on get phone list cause by {}", e.getMessage());
            throw new DataException.ExecuteException(e.getMessage());
        }
    }

    @Override
    public List<String> getPersonalIdList(List<String> personalIdList) {
        try (CustomDSLContext context = getDSLContext()) {
            LOGGER.info("Get personalId list: {}", JsonConverter.toJson(personalIdList));
            return context.select(Tables.USER.PERSONALID)
                    .from(Tables.USER)
                    .where(Tables.USER.PERSONALID.in(personalIdList))
                    .fetchInto(String.class);
        } catch (Exception e) {
            LOGGER.error("Error on get personalId list cause by {}", e.getMessage());
            throw new DataException.ExecuteException(e.getMessage());
        }
    }

    @Override
    public void importUsers(List<ImportedUser> users, String importedBy, UserImportHandler handler) {
        try (CustomDSLContext context = getDSLContext()) {
            LOGGER.info("Import users: {}", JsonConverter.toJson(users));

//            context.transaction(configuration -> {
//                for (ImportedUser user : users) {
//                    UserRecord userRecord = new UserRecord()
//                            .setUserid(UUID.randomUUID().toString())
//                            .setName(user.getName())
//                            .setPersonalid(user.getPersonalId())
//                            .setPhone(user.getPhone())
//                            .setCreatedby(importedBy)
//                            .setCreatedat(LocalDateTime.now())
//                            .setModifiedby(importedBy)
//                            .setModifiedat(LocalDateTime.now());
//
//                    context.insertInto(Tables.USER)
//                            .set(userRecord)
//                            .execute();
//
//                    handler.handle(user);
//                }
//            });

            for (ImportedUser user : users) {
                UserRecord userRecord = new UserRecord()
                        .setUserid(UUID.randomUUID().toString())
                        .setName(user.getName())
                        .setPersonalid(user.getPersonalId())
                        .setPhone(user.getPhone())
                        .setCreatedby(importedBy)
                        .setCreatedat(LocalDateTime.now())
                        .setModifiedby(importedBy)
                        .setModifiedat(LocalDateTime.now());

                context.insertInto(Tables.USER)
                        .set(userRecord)
                        .execute();

//                // TODO: Remove this line. Used for testing long time process
//                TimeUnit.SECONDS.sleep(1);

                handler.handle(user);
            }
        } catch (Exception e) {
            LOGGER.error("Error on import users cause by {}", e.getMessage());
            throw new DataException.ExecuteException(e.getMessage());
        }
    }
}
