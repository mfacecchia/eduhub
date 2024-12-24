package com.feis.eduhub.backend.common.auth.lib;

import com.feis.eduhub.backend.common.interfaces.dao.RedisDao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * {@link com.feis.eduhub.backend.common.interfaces.dao.RedisDao RedisDao}
 * implementation for JWT operations in Redis.
 * This class provides basic CRUD operations on Redis Sets.
 * 
 * @see RedisDao
 */
public class JwtDao implements RedisDao {
    private final String KEY_PREFIX = "JWT:";

    @Override
    public void create(String key, String value, SetParams setParams, Jedis conn) {
        conn.set(KEY_PREFIX + key, value, setParams);
    }

    @Override
    public void delete(String key, Jedis conn) {
        conn.del(KEY_PREFIX + key);
    }

    @Override
    public String findByKey(String key, Jedis conn) {
        return conn.get(KEY_PREFIX + key);
    }

    @Override
    public void update(String key, String value, SetParams setParams, Jedis conn) {
        conn.set(KEY_PREFIX + key, value, setParams);
    }
}
