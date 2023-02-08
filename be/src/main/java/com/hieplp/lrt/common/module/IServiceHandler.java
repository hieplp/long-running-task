package com.hieplp.lrt.common.module;

import com.hieplp.lrt.common.pojo.payload.request.CommonRequest;
import com.hieplp.lrt.common.pojo.payload.response.CommonResponse;

@FunctionalInterface
public interface IServiceHandler {
    CommonResponse handle(CommonRequest request);
}
