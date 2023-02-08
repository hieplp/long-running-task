package com.hieplp.lrt.common.constant;

public class StaticEnum {
    public enum ImportType {
        USER(0),
        ROLE(1);

        private final Integer type;

        ImportType(Integer type) {
            this.type = type;
        }

        public Integer getType() {
            return type;
        }
    }

    public enum Status {
        IN_PROGRESS(0),
        DONE(1);

        private final Integer status;

        Status(Integer status) {
            this.status = status;
        }

        public Integer getStatus() {
            return status;
        }

        public Byte getByteStatus() {
            return status.byteValue();
        }
    }
}
