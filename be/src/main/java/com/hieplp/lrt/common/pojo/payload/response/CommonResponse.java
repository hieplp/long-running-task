package com.hieplp.lrt.common.pojo.payload.response;

import com.hieplp.lrt.common.constant.ICodeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 08/11/2022
 * Time: 16:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    private String code;
    @Builder.Default
    private Object data = new Object();
    private String message;

    public CommonResponse(ICodeResponse codeResponse) {
        this.code = codeResponse.getCode();
        this.message = codeResponse.getMessage();
    }

    public static class CommonResponseBuilder {
        public CommonResponseBuilder response(ICodeResponse codeResponse) {
            this.code = codeResponse.getCode();
            this.message = codeResponse.getMessage();
            return this;
        }
    }
}
