package com.hieplp.lrt.config;

import com.hieplp.lrt.common.config.DbConfig;
import com.hieplp.lrt.common.config.HttpServerConfig;
import com.hieplp.lrt.common.config.RedisConfig;
import com.hieplp.lrt.common.config.VertxConfig;
import lombok.Getter;

@Getter
public class Config extends VertxConfig {
    private HttpServerConfig serverConfig;
    private DbConfig dbConfig;
    private RedisConfig redisConfig;
}
