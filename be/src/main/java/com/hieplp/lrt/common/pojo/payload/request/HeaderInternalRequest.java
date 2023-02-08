package com.hieplp.lrt.common.pojo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 09/11/2022
 * Time: 15:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeaderInternalRequest {
    private String userId;
}
