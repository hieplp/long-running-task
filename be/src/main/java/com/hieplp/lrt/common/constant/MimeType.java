package com.hieplp.lrt.common.constant;

public enum MimeType {
    JSON("application/json");
    private final String contentType;

    MimeType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
