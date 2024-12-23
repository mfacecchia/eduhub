package com.feis.eduhub.backend.common.interfaces;

/**
 * Interface representing the more generic
 * {@link com.feis.eduhub.backend.common.interfaces.Dao Dao} interface as a more
 * suitable alternative for app models (which are used to represent SQL Database
 * entities) providing methods for creation, update, and deletion.
 * 
 * @param <T> The type of entity this DAO handles
 * 
 * @see Dao
 */
public interface ModelDao<T> extends Dao<T> {
    T create(T obj);

    void update(long id, T obj);

    void delete(long id);

}
