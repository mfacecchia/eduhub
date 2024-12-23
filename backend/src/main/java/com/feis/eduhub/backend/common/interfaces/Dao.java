package com.feis.eduhub.backend.common.interfaces;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    T create(T obj);

    Optional<T> findById(long id);

    void update(long id, T obj);

    void delete(long id);

    List<T> getAll();
}
