package com.hieplp.lrt.common.constant;

public class APIConfig {
    private APIConfig() {
        throw new IllegalStateException("Utility class: APIConfig");
    }

    public static class User {
        public static final String USER = "/user";
        public static final String USER_IMPORT = USER + "/import";
    }

    public static class SSE {
        private static final String PREFIX = "/sse";
        public static final String USER = PREFIX + "/user";
    }

    public static class Socket {
        private static final String PREFIX = "/ws";
        public static final String USER = PREFIX + "/user";
    }
}
