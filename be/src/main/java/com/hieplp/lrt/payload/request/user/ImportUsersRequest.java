package com.hieplp.lrt.payload.request.user;

import lombok.Data;

import java.util.List;

@Data
public class ImportUsersRequest {
    private String refKey;
    private List<ImportedUser> users;
}
