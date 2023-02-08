package com.hieplp.lrt.common.pojo.payload.request;

import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 08/12/2022
 * Time: 17:00
 */
@Data
public class QueryRequest {
    private Integer from;
    private Integer limit;
    //
    private String searchBy;
    private String searchValue;
    //
    private String filterBy;
    private String filterValue;
    //
    private String order;
    private String by;
}
