package com.feis.eduhub.backend.common.interfaces;

import java.util.List;

public interface Dao<T> {
    T create(T obj);

    T findById(long id);

    void update(long id, T obj);

    boolean delete(long id);

    List<T> getAll();
}
