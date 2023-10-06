package com.rupesh.shared;

import java.util.List;

import static java.util.Collections.emptyList;

public interface BaseRepository<T extends BaseEntity<K>, K> {

    default T findById(K id) {
        return null;
    }

    default List<T> findBy(String param) {
        return emptyList();
    }

    default List<T> findAll() {
        return emptyList();
    }

    default void save(T entity) {
    }

    default void saveAll(List<T> entities) {
    }

    default void update(T entity) {
    }

    default void delete(K id) {
    }

}
