package com.hieplp.lrt.common.constant;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 9/5/2022
 * Time: 21:39
 **/
public class TemplateConstant {
    private TemplateConstant() {
        throw new IllegalStateException("Utility class: TemplateConstant");
    }

    public static class Template {
        public static final String REGISTER = "REGISTER";
        public static final String FORGOT_PASSWORD = "FORGOT_PASSWORD";

        private Template() {
            throw new IllegalStateException("Utility class: Template");
        }
    }

    public static class Params {
        private Params() {
            throw new IllegalStateException("Utility class: Params");
        }

        public static class Register {
            public static final String LINK = "link";
            public static final String FULL_NAME = "fullName";

            private Register() {
                throw new IllegalStateException("Utility class: Register");
            }
        }

        public static class ForgotPassword {
            public static final String LINK = "link";
            public static final String FULL_NAME = "fullName";

            private ForgotPassword() {
                throw new IllegalStateException("Utility class: ForgotPassword");
            }
        }
    }
}
