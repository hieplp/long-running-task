package com.hieplp.lrt.repositories.sources;

import com.hieplp.lrt.common.handler.UserImportHandler;
import com.hieplp.lrt.payload.request.user.ImportedUser;
import com.hieplp.lrt.repositories.base.IBaseRepo;

import java.util.List;

public interface IUserRepo extends IBaseRepo {
    /**
     * Get list of phone that already exists in database
     *
     * @param phoneList list of phone to check
     * @return list of phone that already exists in database
     */
    List<String> getPhoneList(List<String> phoneList);

    /**
     * Get list of personalId that already exists in database
     *
     * @param personalIdList list of personalId to check
     * @return list of personalId that already exists in database
     */
    List<String> getPersonalIdList(List<String> personalIdList);

    /**
     * Import list of users with transaction
     *
     * @param users      list of users to import
     * @param importedBy user who import
     * @param handler    callback function
     */
    void importUsers(List<ImportedUser> users, String importedBy, UserImportHandler handler);
}
