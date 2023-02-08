package com.hieplp.lrt.common.handler;

import com.hieplp.lrt.payload.request.user.ImportedUser;

@FunctionalInterface
public interface UserImportHandler {
    void handle(ImportedUser user);
}
