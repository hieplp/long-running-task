package com.hieplp.lrt.common.config;

import lombok.Getter;

@Getter
public class DbConfig {
    private String dataSourceClassName;
    private String host;
    private Integer port;
    private Integer maxPoolSize;
    private String username;
    private String password;
    private String database;
    private Boolean debugInfoOnStackTrace;
    private Boolean executeLogging;
}
