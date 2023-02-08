package com.hieplp.lrt.common.pojo.payload.request;

import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 08/11/2022
 * Time: 16:56
 */
@Data
@Builder
public class CommonRequest {
    private JsonObject data;
    private HeaderInternalRequest headers;
}
