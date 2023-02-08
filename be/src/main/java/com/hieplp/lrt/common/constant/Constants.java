package com.hieplp.lrt.common.constant;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class: Constants");
    }

    public static class Token {
        public static final String PASSWORD = "PASSWORD";
        public static final String PRIVATE_JWT = "PRIVATE_JWT";
        public static final String PUBLIC_JWT = "PUBLIC_JWT";

        private Token() {
            throw new IllegalStateException("Utility class: Token");
        }
    }

    public static class JWT {
        public static final String USER = "user";

        private JWT() {
            throw new IllegalStateException("Utility class: JWT");
        }
    }
}
