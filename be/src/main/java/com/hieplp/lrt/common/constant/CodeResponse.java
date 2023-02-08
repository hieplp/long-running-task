package com.hieplp.lrt.common.constant;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 08/11/2022
 * Time: 16:31
 */
public class CodeResponse {

    public enum SuccessCode implements ICodeResponse {
        SUCCESS("200", "success"),
        SSE_SUCCESS("SSE_200", "SSE success"),
        ;

        private final String code;
        private final String message;

        SuccessCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public Integer getIntCode() {
            return Integer.valueOf(message);
        }

        public String getMessage() {
            return message;
        }
    }

    public enum ClientErrorCode implements ICodeResponse {
        FORBIDDEN("403", "Forbidden error"),
        BAD_REQUEST("400", "Bad request error"),
        NOT_FOUND("404", "Not found error"),
        // Auth
        DUPLICATED_PHONE("USER_4000", "Duplicated phone error"),
        DUPLICATED_PERSONAL_ID("USER_4001", "Duplicated personal id error"),
        //
        ;

        private final String code;
        private final String message;

        ClientErrorCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public Integer getIntCode() {
            return Integer.valueOf(message);
        }

        public String getMessage() {
            return message;
        }
    }

    public enum ServerErrorCode implements ICodeResponse {
        INTERNAL_SERVER("500", "Internal Server Error"),
        SSE_SERVER_ERROR("SSE_500", "SSE Server Error"),
        ;

        private final String code;
        private final String message;

        ServerErrorCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public Integer getIntCode() {
            return Integer.valueOf(message);
        }

        public String getMessage() {
            return message;
        }
    }
}
