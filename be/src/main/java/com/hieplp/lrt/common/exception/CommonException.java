package com.hieplp.lrt.common.exception;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 08/11/2022
 * Time: 16:11
 */
public class CommonException extends Exception {
    public static class InvalidConfig extends RuntimeException {
        public InvalidConfig(String message) {
            super(message);
        }
    }

    public static class UnknownException extends RuntimeException {
        public UnknownException(String message) {
            super(message);
        }
    }

    public static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String message) {
            super(message);
        }
    }

    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
}
