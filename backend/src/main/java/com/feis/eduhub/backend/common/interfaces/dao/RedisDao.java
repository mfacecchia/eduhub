package com.feis.eduhub.backend.common.interfaces.dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * Interface for Redis data access operations.
 * 
 * <b>IMPORTANT NOTE:</b> This interface does not extend the base
 * {@link com.feis.eduhub.backend.common.interfaces.dao.Dao DAO} interface due
 * to fundamental differences in Redis operations compared to database-specific
 * operations.
 * 
 * @see Jedis
 */
public interface RedisDao {
    String findByKey(String key, Jedis conn);

    void create(String key, String value, SetParams setParams, Jedis conn);

    void update(String key, String value, SetParams setParams, Jedis conn);

    void delete(String key, Jedis conn);
}
