package com.hieplp.lrt.common.exception;

import java.util.List;

public class DataException extends Exception {
    public static class ExecuteException extends RuntimeException {
        public ExecuteException(String msg) {
            super(msg);
        }
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String msg) {
            super(msg);
        }
    }

    public static class DuplicatedPhoneException extends RuntimeException {
        private final List<String> phones;

        public DuplicatedPhoneException(String msg, List<String> phones) {
            super(msg);
            this.phones = phones;
        }

        public List<String> getPhones() {
            return phones;
        }
    }

    public static class DuplicatedPersonalIdException extends RuntimeException {
        private final List<String> personalIds;

        public DuplicatedPersonalIdException(String msg, List<String> personalIds) {
            super(msg);
            this.personalIds = personalIds;
        }

        public List<String> getPersonalIds() {
            return personalIds;
        }
    }
}

