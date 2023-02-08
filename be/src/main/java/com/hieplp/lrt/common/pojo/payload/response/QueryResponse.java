package com.hieplp.lrt.common.pojo.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class QueryResponse<T> {
    private List<T> list;
    private Integer total;
}
