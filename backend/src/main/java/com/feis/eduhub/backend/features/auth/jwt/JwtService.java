package com.feis.eduhub.backend.features.auth.jwt;

import com.feis.eduhub.backend.common.config.RedisConnection;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

public class JwtService {
    private final RedisConnection redisConnection;
    private final JwtDao jwtDao;

    public JwtService() {
        redisConnection = RedisConnection.getInstance();
        jwtDao = new JwtDao();
    }

    public void storeJwt(String jti, String accountId, long exp) {
        try (Jedis conn = redisConnection.getConnection()) {
            SetParams setParams = new SetParams();
            if (exp > 0) {
                setParams.exAt(exp);
            }
            jwtDao.create(jti, accountId, setParams, conn);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteJwt(String jti) {
        try (Jedis conn = redisConnection.getConnection()) {
            jwtDao.delete(jti, conn);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getJwt(String jti) {
        try (Jedis conn = redisConnection.getConnection()) {
            String result = jwtDao.findByKey(jti, conn);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateJwt(String jti, String accountId, long exp) {
        try (Jedis conn = redisConnection.getConnection()) {
            SetParams setParams = new SetParams();
            setParams.xx();
            if (exp > 0) {
                setParams.exAt(exp);
            }
            jwtDao.update(jti, accountId, setParams, conn);
        } catch (Exception e) {
            throw e;
        }
    }
}
