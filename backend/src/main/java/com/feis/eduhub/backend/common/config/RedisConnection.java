package com.feis.eduhub.backend.common.config;

import java.util.Arrays;
import java.util.Map;

import com.feis.eduhub.backend.common.lib.Environment;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisConnection {
    private static RedisConnection instance;
    private final JedisPool jedisPool;
    private static final Map<String, String> configValues = Environment
            .getEnvironmentVariables(Arrays.asList("REDIS_CONNECTION_STRING"));

    private RedisConnection() {
        jedisPool = new JedisPool(configValues.get("REDIS_CONNECTION_STRING"));
        jedisPool.setMaxTotal(5);
    }

    public static RedisConnection getInstance() {
        if (instance == null) {
            instance = new RedisConnection();
        }
        return instance;
    }

    public Jedis getConnection() {
        return jedisPool.getResource();
    }
}