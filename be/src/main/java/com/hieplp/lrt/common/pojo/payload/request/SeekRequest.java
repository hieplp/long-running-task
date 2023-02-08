package com.hieplp.lrt.common.pojo.payload.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 08/12/2022
 * Time: 17:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SeekRequest extends QueryRequest {
    private String seekField;
    private String seekKey;
    private String seekValue;
}
