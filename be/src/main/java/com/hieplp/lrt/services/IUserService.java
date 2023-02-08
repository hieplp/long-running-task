package com.hieplp.lrt.services;

import com.hieplp.lrt.common.pojo.payload.request.CommonRequest;
import com.hieplp.lrt.common.pojo.payload.response.CommonResponse;

public interface IUserService {
    CommonResponse importUsers(CommonRequest commonRequest);

    CommonResponse getImportUsersData(CommonRequest commonRequest);
}
